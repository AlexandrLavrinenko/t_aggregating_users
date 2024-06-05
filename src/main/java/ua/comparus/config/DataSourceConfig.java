package ua.comparus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ua.comparus.config.property.DatabaseProperty;

@Configuration
public class DataSourceConfig {

    public static final String DB_PREFIX = "[0]";
    public static final String PROPERTY_PREFIX = "db.data-sources";
    public static final String JPA_REPOSITORY_PACKAGE = "ua.comparus.database.repository";
    public static final String ENTITY_PACKAGE = "ua.comparus.database.entity";
    public static final String ENTITY_MANAGER_FACTORY = "oneEntityManagerFactory";
    public static final String DATA_SOURCE = "oneDataSource";
    public static final String DB1_PROPERTY = "oneDatabaseProperty";
    public static final String TRANSACTION_MANAGER = "oneTransactionManager";

    @Value("${db.data-sources[0].name}")
    private static String name;

    @Value("${db.data-sources[0].strategy}")
    private static String strategy;

    @Value("${db.data-sources[0].url}")
    private static String url;

    @Value("${db.data-sources[0].table}")
    private static String table;

    @Value("${db.data-sources[0].user}")
    private static String user;

    @Value("${db.data-sources[0].password}")
    private static String password;

    @Bean(DB1_PROPERTY)
    @Primary
    @ConfigurationProperties(prefix = PROPERTY_PREFIX)
    public DatabaseProperty appDatabaseProperty() {
        return DatabaseProperty.builder()
                .name(name)
                .strategy(strategy)
                .table(table)
                .user(user)
                .password(password)
                .build();
    }
}
