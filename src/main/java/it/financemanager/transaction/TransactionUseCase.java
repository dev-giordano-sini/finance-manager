package it.financemanager.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface TransactionUseCase {
    Page<TransactionResponse> list(LocalDate from, LocalDate to, Pageable pageable);

    TransactionResponse get(Long id);

    TransactionResponse create(TransactionRequest request);

    TransactionResponse update(Long id, TransactionRequest request);

    void delete(Long id);
}
