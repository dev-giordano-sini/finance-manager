package it.financemanager.role.port.out;
import it.financemanager.role.Role;
import java.util.Optional;
public interface RoleOutputPort {
    Optional<Role> findByCode(String code);
    Role save(Role role);
}
