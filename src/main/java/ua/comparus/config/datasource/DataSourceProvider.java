package ua.comparus.config.datasource;

import ua.comparus.config.datasource.MultiTenantDatasourceProperties.DataSourceDefinition;

import javax.sql.DataSource;

public interface DataSourceProvider {

    DataSource buildDataSource(DataSourceDefinition dataSourceDefinition);
}
