package it.financemanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Testcontainers(disabledWithoutDocker = true)
class DatabaseMigrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.test.database.replace", () -> "none");
    }

    @Autowired DataSource dataSource;

    @Test
    void flywayCreatesTheCompleteSchema() throws SQLException {
        try (var connection = dataSource.getConnection();
             var tables = connection.getMetaData().getTables(null, "public", "%", new String[]{"TABLE"})) {
            var names = new java.util.HashSet<String>();
            while (tables.next()) {
                names.add(tables.getString("TABLE_NAME"));
            }
            assertThat(names).contains("users", "categories", "transactions", "budgets", "flyway_schema_history");
        }
    }
}
