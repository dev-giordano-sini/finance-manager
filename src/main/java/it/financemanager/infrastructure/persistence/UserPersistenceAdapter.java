package it.financemanager.infrastructure.persistence;

import it.financemanager.common.exception.ConflictException;
import it.financemanager.user.User;
import it.financemanager.user.UserStore;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class UserPersistenceAdapter implements UserStore {
    private final JpaUserRepository repository;

    UserPersistenceAdapter(JpaUserRepository repository) {
        this.repository = repository;
    }

    @Override public Optional<User> findByEmailIgnoreCase(String email) { return repository.findByEmailIgnoreCase(email); }
    @Override public boolean existsByEmailIgnoreCase(String email) { return repository.existsByEmailIgnoreCase(email); }

    @Override
    public User saveAndFlush(User user) {
        try {
            return repository.saveAndFlush(user);
        } catch (DataIntegrityViolationException exception) {
            throw new ConflictException("An account with this email already exists");
        }
    }
}
