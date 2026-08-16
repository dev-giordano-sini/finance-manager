package it.financemanager.infrastructure.persistence;

import it.financemanager.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

interface TransactionRepository
        extends JpaRepository<TransactionEntity, Long> {
    @EntityGraph(attributePaths = {"user", "user.role", "category",
            "category.user", "category.user.role"})
    Optional<TransactionEntity>
    findByIdAndUserId(Long id, Long userId);

    @EntityGraph(attributePaths = {"user", "user.role", "category",
            "category.user", "category.user.role"})
    Page<TransactionEntity>
    findAllByUserIdAndDateBetween(Long userId, LocalDate from, LocalDate to,
                                  Pageable pageable);

    @Query("""
            select t from TransactionJpaEntity t
            join fetch t.user u
            join fetch u.role
            join fetch t.category c
            join fetch c.user cu
            join fetch cu.role
            where t.user.id = :userId and t.date between :from and :to
            order by t.date desc, t.id desc
            """)
    List<TransactionEntity> findForDashboard(@Param("userId") Long userId,
                                             @Param("from") LocalDate from,
                                             @Param("to") LocalDate to);
}
