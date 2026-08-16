package it.financemanager.category;
import java.util.List;
public interface CategoryUseCase {
  List<CategoryResponse> list();
  CategoryResponse get(Long id);
  CategoryResponse create(SaveCategoryCommand command);
  CategoryResponse update(Long id, SaveCategoryCommand command);
  void delete(Long id);
  Category find(Long id, Long userId);
}
