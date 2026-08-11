package it.financemanager.category;

import it.financemanager.common.exception.ConflictException;
import it.financemanager.common.exception.ResourceNotFoundException;
import it.financemanager.category.port.out.CategoryOutputPort;
import it.financemanager.user.CurrentUserService;
import it.financemanager.user.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryOutputPort repository;
    private final CurrentUserService currentUser;

    public CategoryService(CategoryOutputPort repository, CurrentUserService currentUser) {
        this.repository = repository;
        this.currentUser = currentUser;
    }

    public List<CategoryResponse> list() {
        User user = currentUser.get();
        return repository.findAllByUserIdOrderByNameAsc(user.getId()).stream().map(this::map).toList();
    }

    public CategoryResponse get(Long id) {
        return map(find(id, currentUser.get().getId()));
    }

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        User user = currentUser.get();
        String name = request.name().trim();
        if (repository.existsByUserIdAndNameIgnoreCase(user.getId(), name))
            throw new ConflictException("Category name already exists");
        try {
            return map(repository.saveAndFlush(new Category(user, name, request.color().toUpperCase())));
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Category name already exists");
        }
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        User user = currentUser.get();
        Category category = find(id, user.getId());
        String name = request.name().trim();
        if (!category.getName().equalsIgnoreCase(name) && repository.existsByUserIdAndNameIgnoreCase(user.getId(), name))
            throw new ConflictException("Category name already exists");
        category.update(name, request.color().toUpperCase());
        return map(category);
    }

    @Transactional
    public void delete(Long id) {
        Category category = find(id, currentUser.get().getId());
        try {
            repository.deleteAndFlush(category);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Category is in use and cannot be deleted");
        }
    }

    public Category find(Long id, Long userId) {
        return repository.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }

    private CategoryResponse map(Category c) {
        return new CategoryResponse(c.getId(), c.getName(), c.getColor(), c.getCreatedAt(), c.getUpdatedAt());
    }
}
