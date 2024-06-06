package ua.comparus.config.datasource;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;
import ua.comparus.config.datasource.MultiTenantDatasourceProperties.DataSourceDefinition;

import javax.sql.DataSource;

import static org.springframework.boot.jdbc.DatabaseDriver.POSTGRESQL;


@Component
@RequiredArgsConstructor
public class DefaultDataSourceProvider implements DataSourceProvider {

    @Override
    public DataSource buildDataSource(DataSourceDefinition dataSourceDefinition) {
        return DataSourceBuilder.create()
                .url(dataSourceDefinition.url())
                .username(dataSourceDefinition.user())
                .password(dataSourceDefinition.password())
                //NOTE: The driver class may be implemented via properties and provided from outside
                .driverClassName(POSTGRESQL.getDriverClassName())
                .build();
    }
}
