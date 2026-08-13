package it.financemanager.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import it.financemanager.application.exception.ConflictException;
import it.financemanager.application.exception.InvalidCredentialsException;
import it.financemanager.application.port.out.PasswordPort;
import it.financemanager.application.port.out.TokenPort;
import it.financemanager.application.port.out.UserPort;
import it.financemanager.domain.model.User;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class AuthServiceTest {
    private final InMemoryUsers users = new InMemoryUsers();
    private final PasswordPort passwords = new PasswordPort() {
        public String encode(String raw) {
            return "encoded:" + raw;
        }
        public boolean matches(String raw, String encoded) {
            return encoded.equals("encoded:" + raw);
        }
    };
    private final TokenPort tokens = new TokenPort() {
        public String generate(String subject) {
            return "token:" + subject;
        }
        public long expiresInSeconds() {
            return 3600;
        }
    };
    private final AuthService service = new AuthService(users, passwords, tokens);

    @Test
    void registersNormalizedUserAndIssuesToken() {
        var result = service.register(" Ada ", " Lovelace ", " ADA@Example.COM ", "password");
        assertThat(result.accessToken()).isEqualTo("token:ada@example.com");
        assertThat(users.findByEmail("ada@example.com")).get().satisfies(user -> {
            assertThat(user.name()).isEqualTo("Ada");
            assertThat(user.surname()).isEqualTo("Lovelace");
            assertThat(user.passwordHash()).isEqualTo("encoded:password");
            assertThat(user.role()).isEqualTo("USER");
        });
    }

    @Test
    void rejectsDuplicateRegistration() {
        service.register("Ada", "Lovelace", "ada@example.com", "password");
        assertThatThrownBy(() -> service.register("Ada", "Lovelace", "ADA@example.com", "password"))
            .isInstanceOf(ConflictException.class);
    }

    @Test
    void rejectsUnknownUserAndWrongPasswordWithoutLeakingWhichFailed() {
        assertThatThrownBy(() -> service.login("missing@example.com", "password"))
            .isInstanceOf(InvalidCredentialsException.class);
        service.register("Ada", "Lovelace", "ada@example.com", "password");
        assertThatThrownBy(() -> service.login("ada@example.com", "wrong"))
            .isInstanceOf(InvalidCredentialsException.class);
    }

    private static final class InMemoryUsers implements UserPort {
        private final Map<String, User> values = new HashMap<>();
        public Optional<User> findByEmail(String email) {
            return Optional.ofNullable(values.get(email));
        }
        public boolean existsByEmail(String email) {
            return values.containsKey(email);
        }
        public User create(String email, String passwordHash, String name, String surname, String role) {
            User user = new User(1L, email, passwordHash, name, surname, role, Instant.EPOCH, Instant.EPOCH);
            values.put(email, user);
            return user;
        }
    }
}
