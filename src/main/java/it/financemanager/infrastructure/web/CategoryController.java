package it.financemanager.infrastructure.web;
import it.financemanager.application.port.in.CategoryUseCase;
import it.financemanager.infrastructure.web.dto.ApiDtos.*;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryUseCase u;
    public CategoryController(CategoryUseCase u) {
        this.u = u;
    }
    @GetMapping
    List<CategoryResponse> list() {
        return u.list().stream().map(WebMapper::category).toList();
    }
    @GetMapping("/{id}")
    CategoryResponse get(@PathVariable Long id) {
        return WebMapper.category(u.get(id));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryResponse create(@Valid @RequestBody CategoryRequest r) {
        return WebMapper.category(u.create(r.name(), r.color()));
    }
    @PutMapping("/{id}")
    CategoryResponse update(@PathVariable Long id, @Valid @RequestBody CategoryRequest r) {
        return WebMapper.category(u.update(id, r.name(), r.color()));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        u.delete(id);
    }
}
