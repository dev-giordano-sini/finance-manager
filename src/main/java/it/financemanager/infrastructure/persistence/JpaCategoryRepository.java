package it.financemanager.infrastructure.persistence;

import it.financemanager.infrastructure.persistence.entity.CategoryJpaEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaCategoryRepository extends JpaRepository<CategoryJpaEntity, Long> {
  @EntityGraph(attributePaths = {"user", "user.role"})
  List<CategoryJpaEntity> findAllByUserIdOrderByNameAsc(Long userId);
  @EntityGraph(attributePaths = {"user", "user.role"})
  Optional<CategoryJpaEntity> findByIdAndUserId(Long id, Long userId);
  boolean existsByUserIdAndNameIgnoreCase(Long userId, String name);
}
