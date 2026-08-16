package it.financemanager.infrastructure.persistence;

import it.financemanager.category.Category;
import it.financemanager.category.CategoryStore;
import it.financemanager.common.exception.ConflictException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class CategoryPersistenceAdapter implements CategoryStore {
    private final JpaCategoryRepository repository;

    CategoryPersistenceAdapter(JpaCategoryRepository repository) { this.repository = repository; }
    @Override public List<Category> findAllByUserIdOrderByNameAsc(Long userId) { return repository.findAllByUserIdOrderByNameAsc(userId); }
    @Override public Optional<Category> findByIdAndUserId(Long id, Long userId) { return repository.findByIdAndUserId(id, userId); }
    @Override public boolean existsByUserIdAndNameIgnoreCase(Long userId, String name) { return repository.existsByUserIdAndNameIgnoreCase(userId, name); }

    @Override
    public Category saveAndFlush(Category value) {
        try {
            return repository.saveAndFlush(value);
        } catch (DataIntegrityViolationException exception) {
            throw new ConflictException("Category name already exists");
        }
    }

    @Override public void delete(Category value) { repository.delete(value); }

    @Override
    public void flush() {
        try {
            repository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new ConflictException("Category is in use and cannot be deleted");
        }
    }
}
