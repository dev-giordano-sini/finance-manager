package it.financemanager.transaction;
import java.time.LocalDate; import java.util.List; import java.util.Optional; import org.springframework.data.domain.Page; import org.springframework.data.domain.Pageable;
public interface TransactionStore { Optional<Transaction> findByIdAndUserId(Long id,Long userId); Page<Transaction> findAllByUserIdAndDateBetween(Long userId,LocalDate from,LocalDate to,Pageable pageable); List<Transaction> findForDashboard(Long userId,LocalDate from,LocalDate to); Transaction save(Transaction value); void delete(Transaction value); }
