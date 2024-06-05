package ua.comparus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import ua.comparus.database.entity.User;

import java.io.Serializable;
import java.util.UUID;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationRunner {

    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner.class, args);
//        System.out.println(context.getBean("db2DataSource"));
        User user = User.builder()
                .id(new UUID(1L, 4L))
                .username("lj")
                .name("ljlsjd")
                .surname("kljskl")
                .build();

    }
}