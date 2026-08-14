package it.financemanager.category;
import java.util.List;
public interface CategoryUseCase { List<CategoryResponse> list(); CategoryResponse get(Long id); CategoryResponse create(CategoryRequest request); CategoryResponse update(Long id, CategoryRequest request); void delete(Long id); Category find(Long id, Long userId); }
