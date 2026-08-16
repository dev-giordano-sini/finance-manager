package it.financemanager.infrastructure.web.dashboard;

import it.financemanager.dashboard.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {
  private final DashboardUseCase service;

  public DashboardController(DashboardUseCase service) {
    this.service = service;
  }

  @GetMapping
  DashboardResponse
  get(@RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
      @RequestParam(required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
    return service.get(from, to);
  }
}
