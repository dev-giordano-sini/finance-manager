package it.financemanager.infrastructure.persistence;
import it.financemanager.infrastructure.persistence.entity.RoleJpaEntity;
import it.financemanager.role.*;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
class RolePersistenceAdapter implements RoleStore {
  private final JpaRoleRepository repository;
  RolePersistenceAdapter(JpaRoleRepository repository) {
    this.repository = repository;
  }
  public Role save(Role value) {
    RoleJpaEntity e =
        value.getId() == null
            ? new RoleJpaEntity(value.getCode(), value.getDescription())
            : repository.findById(value.getId()).orElseThrow();
    e.code = value.getCode();
    e.description = value.getDescription();
    return DomainPersistenceMapper.role(repository.save(e));
  }
  public Optional<Role> findByCode(String code) {
    return repository.findByCode(code).map(DomainPersistenceMapper::role);
  }
}
