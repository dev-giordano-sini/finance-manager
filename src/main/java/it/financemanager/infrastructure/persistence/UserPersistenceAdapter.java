package it.financemanager.infrastructure.persistence;
import it.financemanager.common.exception.ConflictException;
import it.financemanager.infrastructure.persistence.entity.*;
import it.financemanager.user.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
class UserPersistenceAdapter implements UserStore {
  private final JpaUserRepository repository;
  private final JpaRoleRepository roles;
  UserPersistenceAdapter(JpaUserRepository repository,
                         JpaRoleRepository roles) {
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
      RoleJpaEntity role = roles.getReferenceById(value.getRole().getId());
      UserJpaEntity e =
          new UserJpaEntity(value.getEmail(), value.getPassword(),
                            value.getName(), value.getSurname(), role);
      return DomainPersistenceMapper.user(repository.saveAndFlush(e));
    } catch (DataIntegrityViolationException ex) {
      throw new ConflictException("An account with this email already exists");
    }
  }
}
