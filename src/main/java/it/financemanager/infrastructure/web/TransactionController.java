package it.financemanager.infrastructure.web;
import it.financemanager.application.port.in.TransactionUseCase;
import it.financemanager.infrastructure.web.dto.ApiDtos.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionUseCase u;
    public TransactionController(TransactionUseCase u) {
        this.u = u;
    }
    @GetMapping
    PageResponse<TransactionResponse> list(@RequestParam(defaultValue = "1900-01-01") LocalDate from,
        @RequestParam(defaultValue = "2999-12-31") LocalDate to, @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {
        var p = u.list(from, to, page, size);
        return new PageResponse<>(p.content().stream().map(WebMapper::transaction).toList(), p.number(), p.size(),
            p.totalElements(), p.totalPages());
    }
    @GetMapping("/{id}")
    TransactionResponse get(@PathVariable Long id) {
        return WebMapper.transaction(u.get(id));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TransactionResponse create(@Valid @RequestBody TransactionRequest r) {
        return WebMapper.transaction(u.create(r.categoryId(), r.type(), r.amount(), r.date(), r.description()));
    }
    @PutMapping("/{id}")
    TransactionResponse update(@PathVariable Long id, @Valid @RequestBody TransactionRequest r) {
        return WebMapper.transaction(u.update(id, r.categoryId(), r.type(), r.amount(), r.date(), r.description()));
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        u.delete(id);
    }
}
