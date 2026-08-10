package it.financemanager.user;

import it.financemanager.auth.AuthResponse;
import it.financemanager.auth.AuthService;
import it.financemanager.auth.LoginRequest;
import it.financemanager.auth.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final CurrentUserService currentUserService;

    public UserController(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    @GetMapping("/me")
    CurrentUserResponse getCurrentUser() {
        return currentUserService.getCurrentUser();
    }
}
