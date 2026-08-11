package it.financemanager.infrastructure.web;
import it.financemanager.budget.BudgetRequest; import it.financemanager.budget.BudgetResponse; import it.financemanager.budget.BudgetService;
import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/v1/budgets")
public class BudgetController { private final BudgetService service; public BudgetController(BudgetService service){this.service=service;}
 @GetMapping List<BudgetResponse> list(){return service.list();} @GetMapping("/{id}") BudgetResponse get(@PathVariable Long id){return service.get(id);}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) BudgetResponse create(@Valid @RequestBody BudgetRequest request){return service.create(request);}
 @PutMapping("/{id}") BudgetResponse update(@PathVariable Long id,@Valid @RequestBody BudgetRequest request){return service.update(id,request);}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) void delete(@PathVariable Long id){service.delete(id);}
}
