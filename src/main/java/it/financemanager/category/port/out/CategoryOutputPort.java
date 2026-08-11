package it.financemanager.category.port.out;

import it.financemanager.category.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryOutputPort {
    List<Category> findAllByUserIdOrderByNameAsc(Long userId);
    Optional<Category> findByIdAndUserId(Long id, Long userId);
    boolean existsByUserIdAndNameIgnoreCase(Long userId, String name);
    Category saveAndFlush(Category category);
    void deleteAndFlush(Category category);
}
