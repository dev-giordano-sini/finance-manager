package it.financemanager.application.port.out;
import it.financemanager.domain.model.User; import java.util.Optional;
public interface UserPort {
 Optional<User> findByEmail(String email); boolean existsByEmail(String email);
 User create(String email, String passwordHash, String name, String surname, String role);
}
