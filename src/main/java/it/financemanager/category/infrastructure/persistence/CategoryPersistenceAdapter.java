package it.financemanager.category.infrastructure.persistence;

import it.financemanager.category.Category;
import it.financemanager.category.port.out.CategoryOutputPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryPersistenceAdapter implements CategoryOutputPort {
    private final SpringDataCategoryRepository repository;

    public CategoryPersistenceAdapter(SpringDataCategoryRepository repository) {
        this.repository = repository;
    }

    @Override public List<Category> findAllByUserIdOrderByNameAsc(Long userId) { return repository.findAllByUserIdOrderByNameAsc(userId); }
    @Override public Optional<Category> findByIdAndUserId(Long id, Long userId) { return repository.findByIdAndUserId(id, userId); }
    @Override public boolean existsByUserIdAndNameIgnoreCase(Long userId, String name) { return repository.existsByUserIdAndNameIgnoreCase(userId, name); }
    @Override public Category saveAndFlush(Category category) { return repository.saveAndFlush(category); }
    @Override public void deleteAndFlush(Category category) { repository.delete(category); repository.flush(); }
}
