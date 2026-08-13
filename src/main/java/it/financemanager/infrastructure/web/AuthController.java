package it.financemanager.infrastructure.web;
import it.financemanager.application.port.in.AuthUseCase;
import it.financemanager.infrastructure.web.dto.ApiDtos.*;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthUseCase useCase;
    public AuthController(AuthUseCase u) {
        useCase = u;
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    AuthResponse register(@Valid @RequestBody RegisterRequest r) {
        var t = useCase.register(r.name(), r.surname(), r.email(), r.password());
        return new AuthResponse(t.accessToken(), t.tokenType(), t.expiresIn());
    }
    @PostMapping("/login")
    AuthResponse login(@Valid @RequestBody LoginRequest r) {
        var t = useCase.login(r.email(), r.password());
        return new AuthResponse(t.accessToken(), t.tokenType(), t.expiresIn());
    }
}
