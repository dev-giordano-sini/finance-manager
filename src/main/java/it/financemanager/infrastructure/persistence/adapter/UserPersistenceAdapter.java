package it.financemanager.infrastructure.persistence.adapter;
import it.financemanager.application.exception.ResourceNotFoundException;
import it.financemanager.application.port.out.UserPort;
import it.financemanager.domain.model.User;
import it.financemanager.infrastructure.persistence.entity.*;
import it.financemanager.infrastructure.persistence.repository.*;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@Transactional
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
        return PersistenceMapper.user(users.save(new UserEntity(e, p, n, s, r)));
    }
}
