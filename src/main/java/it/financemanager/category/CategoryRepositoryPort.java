package it.financemanager.category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryPort {
    List<Category> findAllByUserIdOrderByNameAsc(Long userId);
    Optional<Category> findByIdAndUserId(Long id, Long userId);
    boolean existsByUserIdAndNameIgnoreCase(Long userId, String name);
    <S extends Category> S saveAndFlush(S category);
    void delete(Category category);
    void flush();
}
