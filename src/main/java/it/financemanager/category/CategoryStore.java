package it.financemanager.category;
import java.util.List; import java.util.Optional;
public interface CategoryStore { List<Category> findAllByUserIdOrderByNameAsc(Long userId); Optional<Category> findByIdAndUserId(Long id,Long userId); boolean existsByUserIdAndNameIgnoreCase(Long userId,String name); Category saveAndFlush(Category value); void delete(Category value); void flush(); }
