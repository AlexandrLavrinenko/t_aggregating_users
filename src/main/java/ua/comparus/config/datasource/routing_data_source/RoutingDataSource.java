package ua.comparus.config.datasource.routing_data_source;

import ua.comparus.config.datasource.DataSourceProvider;
import ua.comparus.config.datasource.MultiTenantDatasourceProperties;
import ua.comparus.config.datasource.MultiTenantDatasourceProperties.DataSourceDefinition;
import ua.comparus.config.datasource.TargetDataSourceContextHolder;
import ua.comparus.config.datasource.TargetDataSourceContextHolder.DataSourceContext;
import ua.comparus.exception.NoDataSourceDefinitionsException;
import ua.comparus.exception.TargetDataSourceDoesNotDefinedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RoutingDataSource extends AbstractDataSource {

    private final MultiTenantDatasourceProperties multiTenantDatasourceProperties;
    private final DataSourceProvider dataSourceProvider;
    private final TargetDataSourceContextHolder targetDataSourceContextHolder;
    private final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();
    private final String primaryDataSourceName;

    public RoutingDataSource(MultiTenantDatasourceProperties multiTenantDatasourceProperties,
                             DataSourceProvider dataSourceProvider,
                             TargetDataSourceContextHolder targetDataSourceContextHolder) {
        this.multiTenantDatasourceProperties = multiTenantDatasourceProperties;
        this.dataSourceProvider = dataSourceProvider;
        this.targetDataSourceContextHolder = targetDataSourceContextHolder;
        DataSourceDefinition primaryDataSourceDefinition = multiTenantDatasourceProperties
                .getDataSourceDefinitions()
                .stream()
                .findFirst()
                .orElseThrow(NoDataSourceDefinitionsException::new);
        DataSource primaryDataSource = dataSourceProvider.buildDataSource(primaryDataSourceDefinition);
        this.primaryDataSourceName = primaryDataSourceDefinition.name();
        dataSources.put(primaryDataSourceName, primaryDataSource);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection(username, password);
    }

    private DataSource determineTargetDataSource() {
        log.debug("Determining target DataSource");
        DataSource targetDataSource;
        DataSourceContext dataSourceContext = targetDataSourceContextHolder.getDataSourceContext();
        /*
            NOTE: The problem with the code here is that Hibernate demands Connection on an Application startup.
            But we do not have dataSourceContext on startup. The better way would be to modify this behavior,
            possibly via properties, and replace body of "if" with custom exception.
        */
        if (Objects.isNull(dataSourceContext)) {
            log.warn("DataSource context has not been initialized!");
            return dataSources.get(primaryDataSourceName);
        }
        String dataSourceName = dataSourceContext.name();
        if (dataSources.containsKey(dataSourceName)) {
            log.debug("Found cached DataSource entity");
            targetDataSource = dataSources.get(dataSourceName);
        } else {
            DataSourceDefinition dataSourceDefinition = multiTenantDatasourceProperties.getDataSourceDefinitions()
                    .stream()
                    .filter(s -> s.name().equals(dataSourceName)).findFirst()
                    .orElseThrow(() -> new TargetDataSourceDoesNotDefinedException(dataSourceName));
            targetDataSource = dataSources.computeIfAbsent(dataSourceName, s -> {
                log.debug("Building new DataSource");
                return dataSourceProvider.buildDataSource(dataSourceDefinition);
            });
        }
        return targetDataSource;
    }
}
