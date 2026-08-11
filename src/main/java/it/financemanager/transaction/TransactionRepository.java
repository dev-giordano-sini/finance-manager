package it.financemanager.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, TransactionRepositoryPort {
    @Override
    @Query("""
            select t from Transaction t
            join fetch t.category
            where t.user.id = :userId and t.date between :from and :to
            order by t.date desc, t.id desc
            """)
    List<Transaction> findForDashboard(@Param("userId") Long userId,
                                       @Param("from") LocalDate from,
                                       @Param("to") LocalDate to);
}
