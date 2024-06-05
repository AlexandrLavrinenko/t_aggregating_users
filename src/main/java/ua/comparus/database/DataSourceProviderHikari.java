package ua.comparus.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

import static org.springframework.boot.jdbc.DatabaseDriver.POSTGRESQL;

public class DataSourceProviderHikari implements DataSourceProvider {

    private static final Integer MAX_POOL_SIZE = 10;

    @Override
    public DataSource createDataSource(DataSourceDefinitionProperties.DataSourceDefinition dataSourceDefinition) {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dataSourceDefinition.url());
        hikariConfig.setUsername(dataSourceDefinition.user());
        hikariConfig.setPassword(dataSourceDefinition.password());
        hikariConfig.setDriverClassName(POSTGRESQL.getDriverClassName());
        hikariConfig.setMaximumPoolSize(MAX_POOL_SIZE);
        hikariConfig.setPoolName("DataSourcePool-" + dataSourceDefinition.name() + "-" + hikariConfig.hashCode());
        return new HikariDataSource(hikariConfig);
    }
}
