package it.financemanager.budget;
import it.financemanager.category.*;
import it.financemanager.common.exception.ResourceNotFoundException;
import it.financemanager.user.*;
public class BudgetService implements BudgetUseCase {
  private final BudgetStore repository;
  private final CategoryUseCase categories;
  private final CurrentUserProvider currentUser;
  public BudgetService(BudgetStore repository, CategoryUseCase categories,
                       CurrentUserProvider currentUser) {
    this.repository = repository;
    this.categories = categories;
    this.currentUser = currentUser;
  }
  public List<BudgetResponse> list() {
    return repository
        .findAllByUserIdOrderByStartDateDesc(currentUser.get().getId())
        .stream()
        .map(this::map)
        .toList();
  }
  public BudgetResponse get(Long id) {
    return map(find(id, currentUser.get().getId()));
  }
  public BudgetResponse create(SaveBudgetCommand request) {
    validate(request);
    User user = currentUser.get();
    Category category = categories.find(request.categoryId(), user.getId());
    return map(
        repository.save(new Budget(user, category, request.amount(),
                                   request.startDate(), request.endDate())));
  }
  public BudgetResponse update(Long id, SaveBudgetCommand request) {
    validate(request);
    User user = currentUser.get();
    Budget budget = find(id, user.getId());
    budget.update(categories.find(request.categoryId(), user.getId()),
                  request.amount(), request.startDate(), request.endDate());
    return map(repository.save(budget));
  }
  public void delete(Long id) {
    repository.delete(find(id, currentUser.get().getId()));
  }
  private void validate(SaveBudgetCommand request) {
    if (request.startDate().isAfter(request.endDate()))
      throw new IllegalArgumentException("startDate must not be after endDate");
  }
  private Budget find(Long id, Long userId) {
    return repository.findByIdAndUserId(id, userId)
        .orElseThrow(() -> new ResourceNotFoundException("Budget", id));
  }
  private BudgetResponse map(Budget b) {
    return new BudgetResponse(b.getId(), b.getCategory().getId(),
                              b.getCategory().getName(), b.getAmount(),
                              b.getStartDate(), b.getEndDate(),
                              b.getCreatedAt(), b.getUpdatedAt());
  }
}
