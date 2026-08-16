package it.financemanager.infrastructure.application;

import it.financemanager.dashboard.DashboardResponse;
import it.financemanager.dashboard.DashboardService;
import it.financemanager.dashboard.DashboardUseCase;
import it.financemanager.transaction.TransactionStore;
import it.financemanager.user.CurrentUserProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
class TransactionalDashboardUseCase implements DashboardUseCase {
    private final DashboardService delegate;

    TransactionalDashboardUseCase(TransactionStore transactions,
                                  CurrentUserProvider user, Clock clock) {
        delegate = new DashboardService(transactions, user, clock);
    }

    @Override
    public DashboardResponse get(LocalDate from, LocalDate to) {
        return delegate.get(from, to);
    }
}
