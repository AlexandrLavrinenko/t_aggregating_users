package ua.comparus.database.repository.aspect;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import ua.comparus.config.datasource.MultiTenantDatasourceProperties;
import ua.comparus.config.datasource.TargetDataSourceContextHolder;
import ua.comparus.database.entity.User;
import ua.comparus.exception.MultithreadingTaskFailedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

import static ua.comparus.config.datasource.MultiTenantDatasourceProperties.*;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class DataSourceCallsAggregationAspect {

    private static final String DATASOURCE_NAME_KEY = "dataSourceName";

    private final MultiTenantDatasourceProperties multiTenantDatasourceProperties;
    private final TargetDataSourceContextHolder targetDataSourceContextHolder;

    @Around("execution (* ua.comparus.database.repository.UserRepository.findAll(..))")
    public List<User> aggregateCalls(ProceedingJoinPoint pjp) {
        List<User> result = new ArrayList<>();
        List<String> dataSourceNames = multiTenantDatasourceProperties.getDataSourceDefinitions()
                .stream()
                .map(DataSourceDefinition::name)
                .toList();
        ExecutorService executorService = Executors.newFixedThreadPool(dataSourceNames.size());
        List<CallTargetedDataSourceTask> tasks = new ArrayList<>();
        for (String dataSourceName : dataSourceNames) {
            tasks.add(new CallTargetedDataSourceTask(targetDataSourceContextHolder, dataSourceName, pjp));
        }

        try {
            List<Future<Collection<User>>> executionResult = executorService.invokeAll(tasks);
            for (Future<Collection<User>> future : executionResult) {
                result.addAll(future.get());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MultithreadingTaskFailedException("The thread of aggregation task was interrupted", e);
        } catch (ExecutionException e) {
            throw new MultithreadingTaskFailedException("Failed to collect aggregated data", e);
        }

        return result;
    }

    @Getter
    @RequiredArgsConstructor
    private static class CallTargetedDataSourceTask implements Callable<Collection<User>> {
        private final TargetDataSourceContextHolder targetDataSourceContextHolder;
        private final String dataSourceName;
        private final ProceedingJoinPoint pjp;

        @Override
        public Collection<User> call() {
            try {
                targetDataSourceContextHolder.setDataSourceContext(new TargetDataSourceContextHolder.DataSourceContext(dataSourceName));
                MDC.put(DATASOURCE_NAME_KEY, dataSourceName);
                return (Collection<User>) pjp.proceed();
            } catch (Throwable e) {
                throw new MultithreadingTaskFailedException("Exception occurred while executing aggregating task", e);
            } finally {
                targetDataSourceContextHolder.remove();
                MDC.clear();
            }
        }
    }
}
