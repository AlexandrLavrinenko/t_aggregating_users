package ua.comparus.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import ua.comparus.database.DataSourceContextHolder;
import ua.comparus.database.DataSourceDefinitionProperties;
import ua.comparus.database.DataSourceProvider;

import javax.sql.DataSource;

@Data
@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties(DataSourceDefinitionProperties.class)
public class DataSourceConfiguration {

    DataSourceProvider dataSourceProvider;

    @Bean
    @ConfigurationProperties("spring.datasource.db1")
    public DataSource db1DataSource(DataSourceDefinitionProperties dataSourceDefinitionProperties,
                                    DataSourceProvider dataSourceProvider,
                                    DataSourceContextHolder dataSourceContextHolder) {
        return new DataSource();
    }

}
