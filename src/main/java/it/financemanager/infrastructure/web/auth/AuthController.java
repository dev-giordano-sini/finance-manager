package it.financemanager.infrastructure.web.auth;

import it.financemanager.auth.AuthResponse;
import it.financemanager.auth.AuthUseCase;
import it.financemanager.auth.LoginCommand;
import it.financemanager.auth.RegisterCommand;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthUseCase service;

    public AuthController(AuthUseCase service) {
        this.service = service;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return service.register(
                new RegisterCommand(request.name(), request.surname(), request.email(),
                        request.password()));
    }

    @PostMapping("/login")
    AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return service.login(new LoginCommand(request.email(), request.password()));
    }
}
