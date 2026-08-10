package it.financemanager.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByIdAndUserId(Long id, Long userId);
    Page<Transaction> findAllByUserIdAndDateBetween(Long userId, LocalDate from, LocalDate to, Pageable pageable);

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
