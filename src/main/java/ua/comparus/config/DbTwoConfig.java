package ua.comparus.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import ua.comparus.config.property.DatabaseProperty;

import javax.sql.DataSource;
import java.util.HashMap;

import static org.springframework.boot.jdbc.DatabaseDriver.POSTGRESQL;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = DbTwoConfig.ENTITY_MANAGER_FACTORY,
        transactionManagerRef = DbTwoConfig.TRANSACTION_MANAGER,
        basePackages = DbTwoConfig.JPA_REPOSITORY_PACKAGE
)
public class DbTwoConfig {

    public static final String PROPERTY_PREFIX = "db.data-sources[1]";
    public static final String JPA_REPOSITORY_PACKAGE = "ua.comparus.database.repository";
    public static final String ENTITY_PACKAGE = "ua.comparus.database.entity";
    public static final String ENTITY_MANAGER_FACTORY = "twoEntityManagerFactory";
    public static final String DATA_SOURCE = "twoDataSource";
    public static final String DATABASE_PROPERTY = "twoDatabaseProperty";
    public static final String TRANSACTION_MANAGER = "twoTransactionManager";

    @Bean(DATABASE_PROPERTY)
    @ConfigurationProperties(prefix = PROPERTY_PREFIX)
    public DatabaseProperty appDatabaseProperty() {
        return DatabaseProperty.builder().build();
    }

    @Bean(DATA_SOURCE)
    public DataSource appDataSource(
            @Qualifier(DATABASE_PROPERTY) DatabaseProperty databaseProperty
    ) {
        return DataSourceBuilder
                .create()
                .username(databaseProperty.user())
                .password(databaseProperty.password())
                .url(databaseProperty.url())
                .driverClassName(POSTGRESQL.getDriverClassName())
                .build();
    }

    @Bean(ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean appEntityManager(
            @Qualifier(DATA_SOURCE) DataSource dataSource
    ) {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPersistenceUnitName(ENTITY_MANAGER_FACTORY);
        em.setPackagesToScan(ENTITY_PACKAGE);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        final HashMap<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.validation.mode", "none");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean(TRANSACTION_MANAGER)
    public PlatformTransactionManager sqlSessionTemplate(
            @Qualifier(ENTITY_MANAGER_FACTORY) LocalContainerEntityManagerFactoryBean entityManager,
            @Qualifier(DATA_SOURCE) DataSource dataSource
    ) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager.getObject());
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}
