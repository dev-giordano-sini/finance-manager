package it.financemanager.infrastructure.persistence;

import it.financemanager.infrastructure.persistence.entity.RoleJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaRoleRepository extends JpaRepository<RoleJpaEntity, Long> {
  public Optional<RoleJpaEntity> findByCode(String code);
}
