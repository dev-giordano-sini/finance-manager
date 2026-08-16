package it.financemanager.infrastructure.persistence;

import it.financemanager.category.Category;
import it.financemanager.category.CategoryStore;
import it.financemanager.common.exception.ConflictException;
import it.financemanager.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class CategoryPersistenceAdapter implements CategoryStore {
    private final CategoryRepository repository;
    private final UserRepository users;

    CategoryPersistenceAdapter(CategoryRepository r, UserRepository u) {
        repository = r;
        users = u;
    }

    public List<Category> findAllByUserIdOrderByNameAsc(Long id) {
        return repository.findAllByUserIdOrderByNameAsc(id)
                .stream()
                .map(DomainPersistenceMapper::category)
                .toList();
    }

    public Optional<Category> findByIdAndUserId(Long id, Long uid) {
        return repository.findByIdAndUserId(id, uid).map(
                DomainPersistenceMapper::category);
    }

    public boolean existsByUserIdAndNameIgnoreCase(Long id, String name) {
        return repository.existsByUserIdAndNameIgnoreCase(id, name);
    }

    public Category saveAndFlush(Category v) {
        try {
            CategoryEntity e = v.getId() == null
                    ? new CategoryEntity()
                    : repository.findById(v.getId()).orElseThrow();
            e.user = users.getReferenceById(v.getUser().getId());
            e.name = v.getName();
            e.color = v.getColor();
            return DomainPersistenceMapper.category(repository.saveAndFlush(e));
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Category name already exists");
        }
    }

    public void delete(Category v) {
        repository.deleteById(v.getId());
    }

    public void flush() {
        try {
            repository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Category is in use and cannot be deleted");
        }
    }
}
