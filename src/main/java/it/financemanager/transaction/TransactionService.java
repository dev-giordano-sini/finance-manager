package it.financemanager.transaction;

import it.financemanager.category.Category;
import it.financemanager.category.CategoryUseCase;
import it.financemanager.common.application.PageQuery;
import it.financemanager.common.application.PageResult;
import it.financemanager.common.exception.ResourceNotFoundException;
import it.financemanager.user.CurrentUserProvider;
import it.financemanager.user.User;
import java.time.LocalDate;

public class TransactionService implements TransactionUseCase {
    private final TransactionStore repository; private final CategoryUseCase categories; private final CurrentUserProvider currentUser;
    public TransactionService(TransactionStore repository, CategoryUseCase categories, CurrentUserProvider currentUser) { this.repository=repository; this.categories=categories; this.currentUser=currentUser; }
    public PageResult<TransactionResponse> list(LocalDate from, LocalDate to, PageQuery pageable) {
        if (from.isAfter(to)) throw new IllegalArgumentException("from must not be after to");
        PageResult<Transaction> page = repository.findAllByUserIdAndDateBetween(currentUser.get().getId(), from, to, pageable);
        return new PageResult<>(page.content().stream().map(this::map).toList(), page.page(), page.size(), page.totalElements(), page.totalPages());
    }
    public TransactionResponse get(Long id) { return map(find(id, currentUser.get().getId())); }
    public TransactionResponse create(SaveTransactionCommand request) {
        User user=currentUser.get(); Category category=categories.find(request.categoryId(), user.getId());
        return map(repository.save(new Transaction(user, category, request.type(), request.amount(), request.date(), clean(request.description()))));
    }
    public TransactionResponse update(Long id, SaveTransactionCommand request) {
        User user=currentUser.get(); Transaction value=find(id,user.getId()); Category category=categories.find(request.categoryId(),user.getId());
        value.update(category,request.type(),request.amount(),request.date(),clean(request.description())); return map(value);
    }
    public void delete(Long id) { repository.delete(find(id,currentUser.get().getId())); }
    private Transaction find(Long id, Long userId) { return repository.findByIdAndUserId(id,userId).orElseThrow(() -> new ResourceNotFoundException("Transaction",id)); }
    private String clean(String value) { return value == null || value.isBlank() ? null : value.trim(); }
    private TransactionResponse map(Transaction t) { return new TransactionResponse(t.getId(),t.getCategory().getId(),t.getCategory().getName(),t.getType(),t.getAmount(),t.getDate(),t.getDescription(),t.getCreatedAt(),t.getUpdatedAt()); }
}
