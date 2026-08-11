package it.financemanager.user.port.out;
import it.financemanager.user.User;
import java.util.Optional;
public interface UserOutputPort {
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    User saveAndFlush(User user);
}
