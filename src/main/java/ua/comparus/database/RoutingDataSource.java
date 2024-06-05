package ua.comparus.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.AbstractDataSource;
import ua.comparus.exception.DataSourceDefinitionException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static ua.comparus.database.DataSourceContextHolder.*;

@Slf4j
public class RoutingDataSource extends AbstractDataSource {

    private final DataSourceDefinitionProperties dataSourceDefinitionProperties;
    private final DataSourceProvider dataSourceProvider;
    private final DataSourceContextHolder dataSourceContextHolder;
    private final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();
    private final String defaultDataSourceName;

    public RoutingDataSource(DataSourceDefinitionProperties dataSourceDefinitionProperties,
                             DataSourceProvider dataSourceProvider,
                             DataSourceContextHolder dataSourceContextHolder) throws DataSourceDefinitionException {
        this.dataSourceDefinitionProperties = dataSourceDefinitionProperties;
        this.dataSourceProvider = dataSourceProvider;
        this.dataSourceContextHolder = dataSourceContextHolder;

        DataSourceDefinitionProperties.DataSourceDefinition defaultDataSourceDefinition = dataSourceDefinitionProperties
                .getDataSourceDefinitions()
                .stream()
                .findFirst()
                .orElseThrow(DataSourceDefinitionException::new);

        DataSource defaultDataSource = dataSourceProvider.createDataSource(defaultDataSourceDefinition);
        this.defaultDataSourceName = defaultDataSourceDefinition.name();
        dataSources.put(defaultDataSourceName, defaultDataSource);
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            return targetDataSource().getConnection();
        } catch (DataSourceDefinitionException e) {
            throw new RuntimeException("Unable to get connection", e);
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        try {
            return targetDataSource().getConnection(username, password);
        } catch (DataSourceDefinitionException e) {
            String message = String.format("Unable to get connection with username = [%s]", username);
            throw new RuntimeException(message,e);
        }
    }

    private DataSource targetDataSource() throws DataSourceDefinitionException {
        log.debug("Defining target DataSource");
        DataSource targetDataSource;
        DataSourceContext dataSourceContext = dataSourceContextHolder.getDataSourceContext();

        if (Objects.equals(dataSourceContext, null)) {
            log.warn("DataSource context has not been initialized!");
            return dataSources.get(defaultDataSourceName);
        }

        String dataSourceName = dataSourceContext.name();
        if (dataSources.containsKey(dataSourceName)) {
            log.debug("Found cached DataSource entity");
            targetDataSource = dataSources.get(dataSourceName);
        } else {
            DataSourceDefinitionProperties.DataSourceDefinition dataSourceDefinition = dataSourceDefinitionProperties.getDataSourceDefinitions()
                    .stream()
                    .filter(s -> s.name().equals(dataSourceName)).findFirst()
                    .orElseThrow(() -> new DataSourceDefinitionException(dataSourceName));
            targetDataSource = dataSources.computeIfAbsent(dataSourceName, s -> {
                log.debug("Building new DataSource");
                return dataSourceProvider.createDataSource(dataSourceDefinition);
            });
        }
        return targetDataSource;
    }
}
