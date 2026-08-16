package it.financemanager.infrastructure.persistence;

import it.financemanager.infrastructure.persistence.entity.UserJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaUserRepository extends JpaRepository<UserJpaEntity, Long> {
  @EntityGraph(attributePaths = "role")
  Optional<UserJpaEntity> findByEmailIgnoreCase(String email);
  boolean existsByEmailIgnoreCase(String email);
}
