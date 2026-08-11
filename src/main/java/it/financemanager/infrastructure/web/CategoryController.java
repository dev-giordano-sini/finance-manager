package it.financemanager.infrastructure.web;

import it.financemanager.category.CategoryRequest;
import it.financemanager.category.CategoryResponse;
import it.financemanager.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService service; public CategoryController(CategoryService service) { this.service = service; }
    @GetMapping List<CategoryResponse> list() { return service.list(); }
    @GetMapping("/{id}") CategoryResponse get(@PathVariable Long id) { return service.get(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) CategoryResponse create(@Valid @RequestBody CategoryRequest request) { return service.create(request); }
    @PutMapping("/{id}") CategoryResponse update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) void delete(@PathVariable Long id) { service.delete(id); }
}
