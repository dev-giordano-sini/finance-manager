package it.financemanager.infrastructure.persistence.adapter;
import it.financemanager.application.port.out.BudgetPort;
import it.financemanager.domain.model.Budget;
import it.financemanager.infrastructure.persistence.entity.*;
import it.financemanager.infrastructure.persistence.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@Transactional
public class BudgetPersistenceAdapter implements BudgetPort {
    private final BudgetJpaRepository r;
    private final UserJpaRepository users;
    private final CategoryJpaRepository categories;
    public BudgetPersistenceAdapter(BudgetJpaRepository r, UserJpaRepository u, CategoryJpaRepository c) {
        this.r = r;
        users = u;
        categories = c;
    }
    public List<Budget> findAllByUser(Long id) {
        return r.findAllByUserIdOrderByStartDateDesc(id).stream().map(PersistenceMapper::budget).toList();
    }
    public Optional<Budget> findByIdAndUser(Long id, Long uid) {
        return r.findByIdAndUserId(id, uid).map(PersistenceMapper::budget);
    }
    public Budget create(Long uid, Long cid, BigDecimal a, LocalDate s, LocalDate e) {
        return PersistenceMapper.budget(
            r.save(new BudgetEntity(users.getReferenceById(uid), categories.getReferenceById(cid), a, s, e)));
    }
    public Budget update(Budget b, Long cid, BigDecimal a, LocalDate s, LocalDate e) {
        BudgetEntity x = r.getReferenceById(b.id());
        x.set(categories.getReferenceById(cid), a, s, e);
        return PersistenceMapper.budget(r.save(x));
    }
    public void delete(Budget b) {
        r.deleteById(b.id());
    }
}
