package it.financemanager.application.port.out;
import it.financemanager.domain.model.Category; import java.util.*;
public interface CategoryPort {
 List<Category> findAllByUser(Long userId); Optional<Category> findByIdAndUser(Long id, Long userId);
 boolean existsByName(Long userId, String name); Category create(Long userId, String name, String color);
 Category update(Category category, String name, String color); void delete(Category category);
}
