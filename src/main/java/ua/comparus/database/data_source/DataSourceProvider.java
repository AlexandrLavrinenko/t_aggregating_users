package ua.comparus.database.data_source;

import javax.sql.DataSource;

import static ua.comparus.database.DataSourceDefinitionProperties.DataSourceDefinition;

public interface DataSourceProvider {

    DataSource createDataSource(DataSourceDefinition dataSourceDefinition);
}
