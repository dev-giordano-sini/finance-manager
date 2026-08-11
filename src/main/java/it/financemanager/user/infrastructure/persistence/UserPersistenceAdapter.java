package it.financemanager.user.infrastructure.persistence;
import it.financemanager.user.User;
import it.financemanager.user.port.out.UserOutputPort;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public class UserPersistenceAdapter implements UserOutputPort {
    private final SpringDataUserRepository repository;
    public UserPersistenceAdapter(SpringDataUserRepository repository) { this.repository = repository; }
    @Override public Optional<User> findByEmailIgnoreCase(String email) { return repository.findByEmailIgnoreCase(email); }
    @Override public boolean existsByEmailIgnoreCase(String email) { return repository.existsByEmailIgnoreCase(email); }
    @Override public User saveAndFlush(User user) { return repository.saveAndFlush(user); }
}
