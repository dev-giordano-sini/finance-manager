package it.financemanager.infrastructure.persistence.repository;
import it.financemanager.infrastructure.persistence.entity.CategoryEntity;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAllByUserIdOrderByNameAsc(Long uid);
    Optional<CategoryEntity> findByIdAndUserId(Long id, Long uid);
    boolean existsByUserIdAndNameIgnoreCase(Long uid, String name);
}
