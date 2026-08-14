package it.financemanager.transaction;
import java.time.LocalDate; import org.springframework.data.domain.Page; import org.springframework.data.domain.Pageable;
public interface TransactionUseCase { Page<TransactionResponse> list(LocalDate from, LocalDate to, Pageable pageable); TransactionResponse get(Long id); TransactionResponse create(TransactionRequest request); TransactionResponse update(Long id, TransactionRequest request); void delete(Long id); }
