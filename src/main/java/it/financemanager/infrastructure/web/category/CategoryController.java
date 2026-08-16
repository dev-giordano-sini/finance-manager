package it.financemanager.infrastructure.web.category;

import it.financemanager.category.*;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
  private final CategoryUseCase service;
  public CategoryController(CategoryUseCase service) { this.service = service; }
  @GetMapping
  List<CategoryResponse> list() {
    return service.list();
  }
  @GetMapping("/{id}")
  CategoryResponse get(@PathVariable Long id) {
    return service.get(id);
  }
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  CategoryResponse create(@Valid @RequestBody CategoryRequest request) {
    return service.create(command(request));
  }
  @PutMapping("/{id}")
  CategoryResponse update(@PathVariable Long id,
                          @Valid @RequestBody CategoryRequest request) {
    return service.update(id, command(request));
  }
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void delete(@PathVariable Long id) {
    service.delete(id);
  }
  private SaveCategoryCommand command(CategoryRequest value) {
    return new SaveCategoryCommand(value.name(), value.color());
  }
}
