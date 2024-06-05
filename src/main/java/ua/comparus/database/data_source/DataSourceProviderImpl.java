package ua.comparus.database.data_source;

import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

import static org.springframework.boot.jdbc.DatabaseDriver.POSTGRESQL;
import static ua.comparus.database.DataSourceDefinitionProperties.DataSourceDefinition;

public class DataSourceProviderImpl implements DataSourceProvider {

    @Override
    public DataSource createDataSource(DataSourceDefinition dataSourceDefinition) {
        return DataSourceBuilder.create()
                .url(dataSourceDefinition.url())
                .username(dataSourceDefinition.user())
                .password(dataSourceDefinition.password())
                .driverClassName(POSTGRESQL.getDriverClassName())
                .build();
    }
}
