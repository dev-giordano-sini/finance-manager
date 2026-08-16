package it.financemanager.infrastructure.persistence;

import it.financemanager.infrastructure.persistence.entity.RoleEntity;
import it.financemanager.role.Role;
import it.financemanager.role.RoleStore;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class RolePersistenceAdapter implements RoleStore {
    private final RoleRepository repository;

    RolePersistenceAdapter(RoleRepository repository) {
        this.repository = repository;
    }

    public Role save(Role value) {
        RoleEntity e =
                value.getId() == null
                        ? new RoleEntity(value.getCode(), value.getDescription())
                        : repository.findById(value.getId()).orElseThrow();
        e.code = value.getCode();
        e.description = value.getDescription();
        return DomainPersistenceMapper.role(repository.save(e));
    }

    public Optional<Role> findByCode(String code) {
        return repository.findByCode(code).map(DomainPersistenceMapper::role);
    }
}
