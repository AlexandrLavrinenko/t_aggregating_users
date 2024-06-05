package ua.comparus.database;

import javax.sql.DataSource;

import static ua.comparus.database.DataSourceDefinitionProperties.*;

public interface DataSourceProvider {

    DataSource createDataSource(DataSourceDefinition dataSourceDefinition);
}
