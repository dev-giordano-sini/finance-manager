package it.financemanager.application.port.in;
import it.financemanager.domain.model.Category; import java.util.List;
public interface CategoryUseCase { List<Category> list(); Category get(Long id); Category create(String name,String color); Category update(Long id,String name,String color); void delete(Long id); }
