package it.financemanager.infrastructure.persistence.repository;
import it.financemanager.infrastructure.persistence.entity.TransactionEntity;
import java.time.LocalDate;
import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findByIdAndUserId(Long id, Long uid);
    Page<TransactionEntity> findAllByUserIdAndDateBetween(Long uid, LocalDate from, LocalDate to, Pageable p);
    @Query("select t from TransactionEntity t join fetch t.category where "
        + "t.user.id=:uid and t.date between :from and :to order by t.date "
        + "desc,t.id desc")
    List<TransactionEntity>
    dashboard(@Param("uid") Long uid, @Param("from") LocalDate from, @Param("to") LocalDate to);
}
