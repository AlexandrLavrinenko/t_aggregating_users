package ua.comparus.config.datasource;

import org.springframework.stereotype.Component;

@Component
public class TargetDataSourceContextHolder {

    private final ThreadLocal<DataSourceContext> dataSourceContext = new ThreadLocal<>();

    public DataSourceContext getDataSourceContext() {
        return dataSourceContext.get();
    }

    public void setDataSourceContext(DataSourceContext practiceContext) {
        this.dataSourceContext.set(practiceContext);
    }

    public void remove() {
        dataSourceContext.remove();
    }

    public record DataSourceContext(String name) {

    }
}
