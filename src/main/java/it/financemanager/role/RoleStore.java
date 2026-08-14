package it.financemanager.role;
import java.util.Optional;
public interface RoleStore { Role save(Role role); Optional<Role> findByCode(String code); }
