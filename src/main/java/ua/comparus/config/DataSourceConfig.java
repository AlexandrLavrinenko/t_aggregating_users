package ua.comparus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ua.comparus.config.property.DataSourceInfo;
import ua.comparus.config.property.DataSourceProperties;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dynamicDataSource(DataSourceProperties dataSourceProperties) {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();

        for (DataSourceInfo sourceConfig : dataSourceProperties.getDataSources()) {
            targetDataSources.putIfAbsent(sourceConfig.name(), dataSourceMapping(sourceConfig));
        }
        dynamicRoutingDataSource.setTargetDataSources(targetDataSources);
        return dynamicRoutingDataSource;
    }

    public DataSource dataSourceMapping(DataSourceInfo dataSourceProperties) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceProperties.url());
        dataSource.setUsername(dataSourceProperties.user());
        dataSource.setPassword(dataSourceProperties.password());
        return dataSource;
    }
}
