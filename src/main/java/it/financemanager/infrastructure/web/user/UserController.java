package it.financemanager.infrastructure.web.user;

import it.financemanager.user.*;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final CurrentUserUseCase currentUserService;

    public UserController(CurrentUserUseCase currentUserService) {
        this.currentUserService = currentUserService;
    }

    @GetMapping("/me")
    CurrentUserResponse getCurrentUser() {
        return currentUserService.getCurrentUser();
    }
}
