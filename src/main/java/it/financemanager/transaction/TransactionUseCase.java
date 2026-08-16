package it.financemanager.transaction;

import it.financemanager.common.application.PageQuery;
import it.financemanager.common.application.PageResult;
import java.time.LocalDate;

public interface TransactionUseCase {
    PageResult<TransactionResponse> list(LocalDate from, LocalDate to, PageQuery pageQuery);
    TransactionResponse get(Long id);
    TransactionResponse create(SaveTransactionCommand command);
    TransactionResponse update(Long id, SaveTransactionCommand command);
    void delete(Long id);
}
