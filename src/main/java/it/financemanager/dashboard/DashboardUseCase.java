package it.financemanager.dashboard;
import java.time.LocalDate;
public interface DashboardUseCase {
  DashboardResponse get(LocalDate from, LocalDate to);
}
