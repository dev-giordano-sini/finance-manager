package it.financemanager.infrastructure.persistence.adapter;
import it.financemanager.application.exception.ConflictException;
import it.financemanager.application.exception.ResourceNotFoundException;
import it.financemanager.application.port.out.UserPort;
import it.financemanager.domain.model.User;
import it.financemanager.infrastructure.persistence.entity.*;
import it.financemanager.infrastructure.persistence.repository.*;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
@Component
public class UserPersistenceAdapter implements UserPort {
    private final UserJpaRepository users;
    private final RoleJpaRepository roles;
    public UserPersistenceAdapter(UserJpaRepository u, RoleJpaRepository r) {
        users = u;
        roles = r;
    }
    public Optional<User> findByEmail(String e) {
        return users.findByEmailIgnoreCase(e).map(PersistenceMapper::user);
    }
    public boolean existsByEmail(String e) {
        return users.existsByEmailIgnoreCase(e);
    }
    public User create(String e, String p, String n, String s, String role) {
        RoleEntity r = roles.findByCode(role).orElseThrow(() -> new ResourceNotFoundException("Role", 0L));
        try {
            return PersistenceMapper.user(users.saveAndFlush(new UserEntity(e, p, n, s, r)));
        } catch (DataIntegrityViolationException exception) {
            throw new ConflictException("An account with this email already exists");
        }
    }
}
