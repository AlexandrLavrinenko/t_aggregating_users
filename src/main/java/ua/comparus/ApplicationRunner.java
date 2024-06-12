package ua.comparus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import ua.comparus.config.datasource.MultiTenantDatasourceProperties;
import ua.comparus.database.repository.UserRepository;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationRunner {

    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner.class, args);
        System.out.println(context.getBeanNamesForType(UserRepository.class));
        System.out.println(context.getBeanNamesForType(MultiTenantDatasourceProperties.DataSourceDefinition.class));
        System.out.println(context.getBean("dataSourceDefinitions"));
    }
}