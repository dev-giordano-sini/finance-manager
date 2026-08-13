package it.financemanager.architecture;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class HexagonalArchitectureTest {
    private static final Path ROOT = Path.of("src/main/java/it/financemanager");

    @Test
    void domainAndApplicationAreFrameworkIndependent() throws IOException {
        assertNoReferences(ROOT.resolve("domain"), "org.springframework", "jakarta.", "it.financemanager.application",
            "it.financemanager.infrastructure");
        assertNoReferences(ROOT.resolve("application"), "org.springframework", "jakarta.persistence", "jakarta.servlet",
            "jakarta.validation", "it.financemanager.infrastructure");
    }

    @Test
    void springDataAndJpaAreConfinedToPersistenceInfrastructure() throws IOException {
        List<String> violations = new ArrayList<>();
        try (var files = Files.walk(ROOT)) {
            files.filter(this::isJava)
                .filter(path -> !path.toString().contains("/infrastructure/persistence/"))
                .forEach(path -> check(path, violations, "JpaRepository", "jakarta.persistence"));
        }
        assertThat(violations).isEmpty();
    }

    @Test
    void webControllersDependOnInputPortsInsteadOfApplicationServices() throws IOException {
        List<String> violations = new ArrayList<>();
        try (var files = Files.walk(ROOT.resolve("infrastructure/web"))) {
            files.filter(path -> path.getFileName().toString().endsWith("Controller.java"))
                .forEach(path -> check(path, violations, "application.service", "application.port.out"));
        }
        assertThat(violations).isEmpty();
    }

    private void assertNoReferences(Path root, String... forbidden) throws IOException {
        List<String> violations = new ArrayList<>();
        try (var files = Files.walk(root)) {
            files.filter(this::isJava).forEach(path -> check(path, violations, forbidden));
        }
        assertThat(violations).isEmpty();
    }

    private boolean isJava(Path path) {
        return path.getFileName().toString().endsWith(".java");
    }

    private void check(Path path, List<String> violations, String... forbidden) {
        try {
            String source = Files.readString(path);
            for (String reference : forbidden) {
                if (source.contains(reference)) {
                    violations.add(path + " references " + reference);
                }
            }
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
