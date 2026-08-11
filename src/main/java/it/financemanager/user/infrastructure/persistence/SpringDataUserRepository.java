package it.financemanager.user.infrastructure.persistence;
import it.financemanager.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
interface SpringDataUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
