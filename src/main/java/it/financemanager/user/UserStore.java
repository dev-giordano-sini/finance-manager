package it.financemanager.user;
import java.util.Optional;
public interface UserStore {
  Optional<User> findByEmailIgnoreCase(String email);
  boolean existsByEmailIgnoreCase(String email);
  User saveAndFlush(User user);
}
