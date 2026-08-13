package it.financemanager.infrastructure.web;
import it.financemanager.application.port.in.DashboardUseCase;
import it.financemanager.infrastructure.web.dto.ApiDtos.*;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
    private final DashboardUseCase u;
    public DashboardController(DashboardUseCase u) {
        this.u = u;
    }
    @GetMapping
    DashboardResponse get(
        @RequestParam(required = false) LocalDate from, @RequestParam(required = false) LocalDate to) {
        var d = u.get(from, to);
        return new DashboardResponse(d.from(), d.to(), d.totalIncome(), d.totalExpenses(), d.balance(),
            d.transactionCount(),
            d.expensesByCategory()
                .stream()
                .map(x
                    -> new CategoryExpense(
                        x.categoryId(), x.categoryName(), x.categoryColor(), x.amount(), x.percentage()))
                .toList(),
            d.dailyCashFlow()
                .stream()
                .map(x -> new DailyCashFlow(x.date(), x.income(), x.expenses(), x.balance()))
                .toList(),
            d.recentTransactions().stream().map(WebMapper::transaction).toList());
    }
}
