package it.financemanager.architecture;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HexagonalArchitectureTest {
    private static final Path SOURCES = Path.of("src/main/java/it/financemanager");

    @Test
    void applicationPortsDoNotImportFrameworkTypes() throws IOException {
        List<Path> violations = javaSources()
                .filter(path -> isApplicationPort(path) || path.toString().contains("/common/application/"))
                .filter(path -> containsAny(path, "org.springframework", "jakarta.persistence", "jakarta.validation"))
                .toList();

        assertThat(violations).as("framework imports in application ports").isEmpty();
    }

    @Test
    void springDataIsConfinedToPersistenceInfrastructure() throws IOException {
        List<Path> violations = javaSources()
                .filter(path -> containsAny(path, "org.springframework.data.jpa", "JpaRepository"))
                .filter(path -> !path.toString().contains("/infrastructure/persistence/")
                        && !path.toString().endsWith("/infrastructure/config/JpaConfig.java")
                        && !path.toString().endsWith("/common/BaseEntity.java"))
                .toList();

        assertThat(violations).as("Spring Data usage outside persistence infrastructure").isEmpty();
    }

    @Test
    void controllersDoNotDependOnConcreteServices() throws IOException {
        List<Path> violations = javaSources()
                .filter(path -> path.getFileName().toString().endsWith("Controller.java"))
                .filter(path -> containsAny(path, "private final AuthService", "private final BudgetService",
                        "private final CategoryService", "private final DashboardService",
                        "private final TransactionService", "private final CurrentUserService"))
                .toList();

        assertThat(violations).as("controllers coupled to concrete use-case implementations").isEmpty();
    }

    @Test
    void webAndConfigurationTypesAreInfrastructureAdapters() throws IOException {
        List<Path> violations = javaSources()
                .filter(path -> containsAny(path, "@RestController", "@RestControllerAdvice",
                        "@Configuration", "jakarta.validation"))
                .filter(path -> !path.toString().contains("/infrastructure/"))
                .toList();

        assertThat(violations).as("framework entry points outside infrastructure").isEmpty();
    }

    @Test
    void applicationServicesDoNotImportSpring() throws IOException {
        List<Path> violations = javaSources()
                .filter(path -> path.getFileName().toString().endsWith("Service.java"))
                .filter(path -> !path.toString().contains("/infrastructure/"))
                .filter(path -> containsAny(path, "org.springframework", "jakarta.transaction"))
                .toList();

        assertThat(violations).as("framework imports in application services").isEmpty();
    }

    private java.util.stream.Stream<Path> javaSources() throws IOException {
        return Files.walk(SOURCES).filter(path -> path.toString().endsWith(".java"));
    }

    private boolean isApplicationPort(Path path) {
        if (path.toString().contains("/infrastructure/")) return false;
        String name = path.getFileName().toString();
        return name.endsWith("UseCase.java") || name.endsWith("Store.java")
                || name.endsWith("Provider.java") || name.endsWith("Identity.java")
                || name.endsWith("Issuer.java") || name.endsWith("Hasher.java")
                || name.endsWith("Authenticator.java") || name.endsWith("Command.java");
    }

    private boolean containsAny(Path path, String... fragments) {
        try {
            String source = Files.readString(path);
            return java.util.Arrays.stream(fragments).anyMatch(source::contains);
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot inspect " + path, exception);
        }
    }
}
