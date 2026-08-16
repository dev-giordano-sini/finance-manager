package it.financemanager.transaction;

import jakarta.validation.Valid;
import it.financemanager.common.application.PageQuery;
import it.financemanager.common.application.PageResult;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionUseCase service;

    public TransactionController(TransactionUseCase service) {
        this.service = service;
    }

    @GetMapping
    PageResult<TransactionResponse> list(@RequestParam(defaultValue = "1900-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                   @RequestParam(defaultValue = "2999-12-31") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                   @PageableDefault(size = 20, sort = "date", direction = Sort.Direction.DESC) Pageable pageable) {
        String sortBy = pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse("date");
        PageQuery.Direction direction = pageable.getSort().stream().findFirst().map(Sort.Order::isAscending)
                .orElse(false) ? PageQuery.Direction.ASC : PageQuery.Direction.DESC;
        return service.list(from, to, new PageQuery(pageable.getPageNumber(), pageable.getPageSize(), sortBy, direction));
    }

    @GetMapping("/{id}")
    TransactionResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TransactionResponse create(@Valid @RequestBody TransactionRequest request) {
        return service.create(command(request));
    }

    @PutMapping("/{id}")
    TransactionResponse update(@PathVariable Long id, @Valid @RequestBody TransactionRequest request) {
        return service.update(id, command(request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        service.delete(id);
    }

    private SaveTransactionCommand command(TransactionRequest value) {
        return new SaveTransactionCommand(value.categoryId(), value.type(), value.amount(), value.date(), value.description());
    }
}
