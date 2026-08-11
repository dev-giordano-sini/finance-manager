package it.financemanager.role.infrastructure.persistence;
import it.financemanager.role.Role;
import it.financemanager.role.port.out.RoleOutputPort;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public class RolePersistenceAdapter implements RoleOutputPort {
    private final SpringDataRoleRepository repository;
    public RolePersistenceAdapter(SpringDataRoleRepository repository) { this.repository = repository; }
    @Override public Optional<Role> findByCode(String code) { return repository.findByCode(code); }
    @Override public Role save(Role role) { return repository.save(role); }
}
