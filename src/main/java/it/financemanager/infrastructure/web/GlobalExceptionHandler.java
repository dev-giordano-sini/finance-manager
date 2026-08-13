package it.financemanager.infrastructure.web;
import it.financemanager.application.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ProblemDetail notFound(Exception e, HttpServletRequest r) {
        return p(HttpStatus.NOT_FOUND, "Resource not found", e.getMessage(), r);
    }
    @ExceptionHandler({ConflictException.class, DataIntegrityViolationException.class})
    ProblemDetail conflict(Exception e, HttpServletRequest r) {
        return p(HttpStatus.CONFLICT, "Conflict",
            e instanceof ConflictException ? e.getMessage() : "Resource conflicts with existing data", r);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    ProblemDetail unauthorized(Exception e, HttpServletRequest r) {
        return p(HttpStatus.UNAUTHORIZED, "Unauthorized", e.getMessage(), r);
    }
    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
    ProblemDetail bad(Exception e, HttpServletRequest r) {
        return p(HttpStatus.BAD_REQUEST, "Bad request", e.getMessage(), r);
    }
    private ProblemDetail p(HttpStatus s, String t, String m, HttpServletRequest r) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(s, m);
        p.setTitle(t);
        p.setInstance(URI.create(r.getRequestURI()));
        return p;
    }
}
