package it.financemanager.infrastructure.web.user;

import it.financemanager.user.CurrentUserResponse;
import it.financemanager.user.CurrentUserUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
