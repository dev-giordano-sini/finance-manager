package it.financemanager.infrastructure.persistence;

import it.financemanager.common.exception.ConflictException;
import it.financemanager.infrastructure.persistence.entity.RoleEntity;
import it.financemanager.infrastructure.persistence.entity.UserEntity;
import it.financemanager.user.User;
import it.financemanager.user.UserStore;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class UserPersistenceAdapter implements UserStore {
    private final UserRepository repository;
    private final RoleRepository roles;

    UserPersistenceAdapter(UserRepository repository,
                           RoleRepository roles) {
        this.repository = repository;
        this.roles = roles;
    }

    public Optional<User> findByEmailIgnoreCase(String email) {
        return repository.findByEmailIgnoreCase(email).map(
                DomainPersistenceMapper::user);
    }

    public boolean existsByEmailIgnoreCase(String email) {
        return repository.existsByEmailIgnoreCase(email);
    }

    public User saveAndFlush(User value) {
        try {
            RoleEntity role = roles.getReferenceById(value.getRole().getId());
            UserEntity e =
                    new UserEntity(value.getEmail(), value.getPassword(),
                            value.getName(), value.getSurname(), role);
            return DomainPersistenceMapper.user(repository.saveAndFlush(e));
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("An account with this email already exists");
        }
    }
}
