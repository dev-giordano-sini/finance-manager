package it.financemanager.role.infrastructure.persistence;
import it.financemanager.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
interface SpringDataRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByCode(String code);
}
