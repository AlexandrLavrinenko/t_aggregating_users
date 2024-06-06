package ua.comparus.integration;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import ua.comparus.integration.annotation.IT;

@IT
@Sql(scripts = {
        "classpath:sql/data.sql"
})
public class IntegrationTestBase {
    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>("postgres:16.3");

    @BeforeAll
    static void runContainer() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("db.data-source-definitions.url", container::getJdbcUrl);
    }
}
