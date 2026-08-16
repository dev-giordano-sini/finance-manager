package it.financemanager.infrastructure.persistence;

import it.financemanager.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    @EntityGraph(attributePaths = {"user", "user.role"})
    List<CategoryEntity> findAllByUserIdOrderByNameAsc(Long userId);

    @EntityGraph(attributePaths = {"user", "user.role"})
    Optional<CategoryEntity> findByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndNameIgnoreCase(Long userId, String name);
}
