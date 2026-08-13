package it.financemanager.infrastructure.persistence.adapter;
import it.financemanager.application.port.out.CategoryPort;
import it.financemanager.domain.model.Category;
import it.financemanager.infrastructure.persistence.entity.*;
import it.financemanager.infrastructure.persistence.repository.*;
import java.util.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@Transactional
public class CategoryPersistenceAdapter implements CategoryPort {
    private final CategoryJpaRepository r;
    private final UserJpaRepository users;
    public CategoryPersistenceAdapter(CategoryJpaRepository r, UserJpaRepository u) {
        this.r = r;
        users = u;
    }
    public List<Category> findAllByUser(Long id) {
        return r.findAllByUserIdOrderByNameAsc(id).stream().map(PersistenceMapper::category).toList();
    }
    public Optional<Category> findByIdAndUser(Long id, Long uid) {
        return r.findByIdAndUserId(id, uid).map(PersistenceMapper::category);
    }
    public boolean existsByName(Long uid, String n) {
        return r.existsByUserIdAndNameIgnoreCase(uid, n);
    }
    public Category create(Long uid, String n, String c) {
        return PersistenceMapper.category(r.save(new CategoryEntity(users.getReferenceById(uid), n, c)));
    }
    public Category update(Category x, String n, String c) {
        CategoryEntity e = r.getReferenceById(x.id());
        e.name = n;
        e.color = c;
        return PersistenceMapper.category(r.save(e));
    }
    public void delete(Category x) {
        r.deleteById(x.id());
        r.flush();
    }
}
