package it.financemanager.auth;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticateUserUseCase authenticateUser;
    private final RegisterUserUseCase registerUser;

    public AuthController(AuthenticateUserUseCase authenticateUser, RegisterUserUseCase registerUser) {
        this.authenticateUser = authenticateUser;
        this.registerUser = registerUser;
    }
    @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED)
    AuthResponse register(@Valid @RequestBody RegisterRequest request) { return registerUser.register(request); }
    @PostMapping("/login")
    AuthResponse login(@Valid @RequestBody LoginRequest request) { return authenticateUser.login(request); }
}
