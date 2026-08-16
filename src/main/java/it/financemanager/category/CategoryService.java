package it.financemanager.category;

import it.financemanager.common.exception.ConflictException;
import it.financemanager.common.exception.ResourceNotFoundException;
import it.financemanager.user.CurrentUserService;
import it.financemanager.user.User;

import java.util.List;

public class CategoryService implements CategoryUseCase {
    private final CategoryStore repository;
    private final CurrentUserProvider currentUser;

    public CategoryService(CategoryStore repository, CurrentUserProvider currentUser) {
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

    public CategoryResponse create(SaveCategoryCommand request) {
        User user = currentUser.get();
        String name = request.name().trim();
        if (repository.existsByUserIdAndNameIgnoreCase(user.getId(), name))
            throw new ConflictException("Category name already exists");
        return map(repository.saveAndFlush(new Category(user, name, request.color().toUpperCase())));
    }

    public CategoryResponse update(Long id, SaveCategoryCommand request) {
        User user = currentUser.get();
        Category category = find(id, user.getId());
        String name = request.name().trim();
        if (!category.getName().equalsIgnoreCase(name) && repository.existsByUserIdAndNameIgnoreCase(user.getId(), name))
            throw new ConflictException("Category name already exists");
        category.update(name, request.color().toUpperCase());
        return map(category);
    }

    public void delete(Long id) {
        Category category = find(id, currentUser.get().getId());
        repository.delete(category);
        repository.flush();
    }

    public Category find(Long id, Long userId) {
        return repository.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }

    private CategoryResponse map(Category c) {
        return new CategoryResponse(c.getId(), c.getName(), c.getColor(), c.getCreatedAt(), c.getUpdatedAt());
    }
}
