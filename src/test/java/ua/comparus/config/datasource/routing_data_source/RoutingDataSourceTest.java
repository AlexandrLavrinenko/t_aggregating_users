package ua.comparus.config.datasource.routing_data_source;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import ua.comparus.config.datasource.DataSourceProvider;
import ua.comparus.config.datasource.MultiTenantDatasourceProperties;
import ua.comparus.config.datasource.TargetDataSourceContextHolder;
import ua.comparus.exception.NoDataSourceDefinitionsException;
import ua.comparus.exception.TargetDataSourceDoesNotDefinedException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ua.comparus.config.datasource.MultiTenantDatasourceProperties.*;
import static ua.comparus.config.datasource.TargetDataSourceContextHolder.*;

class RoutingDataSourceTest {

    private static final String DATABASE_NAME_1 = "TestDB1";
    private static final String DATABASE_NAME_2 = "TestDB2";

    private final MultiTenantDatasourceProperties properties = mock(MultiTenantDatasourceProperties.class);
    private final DataSourceProvider provider = mock(DataSourceProvider.class);
    private final TargetDataSourceContextHolder contextHolder = mock(TargetDataSourceContextHolder.class);
    private final DataSourceContext context = mock(DataSourceContext.class);
    private final DataSourceDefinition definition1 = mock(DataSourceDefinition.class);
    private final DataSourceDefinition definition2 = mock(DataSourceDefinition.class);
    private final DataSource dataSource1 = mock(DataSource.class);
    private final DataSource dataSource2 = mock(DataSource.class);
    private final Connection connection = mock(Connection.class);
    private RoutingDataSource routingDataSource;

    @BeforeEach
    void setup(TestInfo info) {
        if (info.getDisplayName().equals("routingDataSourceConstructorNoDataSourceDefinitionsExceptionTest()")) {
            return; //Skipping creation RoutingDataSource for the constructor exception test.
        }
        when(properties.getDataSourceDefinitions()).thenReturn(List.of(definition1, definition2));
        when(provider.buildDataSource(definition1)).thenReturn(dataSource1);
        when(definition1.name()).thenReturn(DATABASE_NAME_1);
        routingDataSource = new RoutingDataSource(properties, provider, contextHolder);
    }

    @Test
    void getConnection() throws SQLException {

        when(contextHolder.getDataSourceContext()).thenReturn(context);
        when(context.name()).thenReturn(DATABASE_NAME_2);
        when(properties.getDataSourceDefinitions()).thenReturn(List.of(definition1, definition2));
        when(definition1.name()).thenReturn(DATABASE_NAME_1);
        when(definition2.name()).thenReturn(DATABASE_NAME_2);
        when(provider.buildDataSource(definition2)).thenReturn(dataSource2);
        when(dataSource2.getConnection()).thenReturn(connection);

        Connection actualResult = routingDataSource.getConnection();

        assertNotNull(actualResult);
        verify(contextHolder, times(1)).getDataSourceContext();
        verify(context, times(1)).name();
        verify(properties, times(2)).getDataSourceDefinitions();
        verify(definition1, times(2)).name();
        verify(definition2, times(1)).name();
        verify(provider, times(1)).buildDataSource(definition1);
        verify(provider, times(1)).buildDataSource(definition2);
        verify(dataSource2, times(1)).getConnection();
        verifyNoMoreInteractions(properties, provider, contextHolder, context, definition1, definition2, dataSource1,
                dataSource2, connection);
    }

    @Test
    void getConnectionNoDatasourceContextIsPresentTest() throws SQLException {

        when(contextHolder.getDataSourceContext()).thenReturn(null);
        when(dataSource1.getConnection()).thenReturn(connection);

        Connection actualResult = routingDataSource.getConnection();

        assertNotNull(actualResult);
        verify(properties, times(1)).getDataSourceDefinitions();
        verify(definition1, times(1)).name();
        verify(provider, times(1)).buildDataSource(definition1);
        verify(contextHolder, times(1)).getDataSourceContext();
        verify(dataSource1, times(1)).getConnection();
        verifyNoMoreInteractions(properties, provider, contextHolder, context, definition1, definition2, dataSource1,
                dataSource2, connection);
    }

    @Test
    void getConnectionDataSourceEntityAlreadyPresentTest() throws SQLException {

        when(contextHolder.getDataSourceContext()).thenReturn(context);
        when(context.name()).thenReturn(DATABASE_NAME_1);
        when(dataSource1.getConnection()).thenReturn(connection);

        Connection actualResult = routingDataSource.getConnection();

        assertNotNull(actualResult);
        verify(properties, times(1)).getDataSourceDefinitions();
        verify(definition1, times(1)).name();
        verify(provider, times(1)).buildDataSource(definition1);
        verify(contextHolder, times(1)).getDataSourceContext();
        verify(context, times(1)).name();
        verify(dataSource1, times(1)).getConnection();
        verifyNoMoreInteractions(properties, provider, contextHolder, context, definition1, definition2, dataSource1,
                dataSource2, connection);
    }

    @Test
    void getConnectionTargetDataSourceDoesNotDefinedExceptionTest() {

        when(contextHolder.getDataSourceContext()).thenReturn(context);
        when(context.name()).thenReturn(DATABASE_NAME_2);
        when(properties.getDataSourceDefinitions()).thenReturn(List.of(definition1));
        when(definition1.name()).thenReturn(DATABASE_NAME_1);

        assertThrows(TargetDataSourceDoesNotDefinedException.class,
                () -> routingDataSource.getConnection("test", "test"));

        verify(contextHolder, times(1)).getDataSourceContext();
        verify(context, times(1)).name();
        verify(properties, times(2)).getDataSourceDefinitions();
        verify(definition1, times(2)).name();
        verify(provider, times(1)).buildDataSource(definition1);
        verifyNoMoreInteractions(properties, provider, contextHolder, context, definition1, definition2, dataSource1,
                dataSource2, connection);
    }

    @Test
    void routingDataSourceConstructorNoDataSourceDefinitionsExceptionTest() {

        when(properties.getDataSourceDefinitions()).thenReturn(Collections.emptyList());
        assertThrows(NoDataSourceDefinitionsException.class,
                () -> new RoutingDataSource(properties, provider, contextHolder));

        verify(properties, times(1)).getDataSourceDefinitions();
        verifyNoMoreInteractions(properties, provider, contextHolder, context, definition1, definition2, dataSource1,
                dataSource2, connection);
    }
}