package it.financemanager.infrastructure.persistence;
import it.financemanager.role.*; import org.springframework.stereotype.Repository; import java.util.*;
@Repository class RolePersistenceAdapter implements RoleStore { private final JpaRoleRepository repository; RolePersistenceAdapter(JpaRoleRepository repository){this.repository=repository;} public Role save(Role role){return repository.save(role);} public Optional<Role> findByCode(String code){return repository.findByCode(code);} }
