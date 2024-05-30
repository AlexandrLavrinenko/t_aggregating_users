package ua.comparus.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfiguration {

    @Primary
    @Bean("db1DataSource")
    @ConfigurationProperties("spring.datasource.db1")
    public DataSourceProperties db1DataSource() {
        return new DataSourceProperties();
    }

    @Bean("db2DataSource")
    @ConfigurationProperties("spring.datasource.db2")
    public DataSourceProperties db2DataSource() {
        return new DataSourceProperties();
    }

}
