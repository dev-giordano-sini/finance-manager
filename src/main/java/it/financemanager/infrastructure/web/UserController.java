package it.financemanager.infrastructure.web;

import it.financemanager.user.CurrentUserResponse;
import it.financemanager.user.CurrentUserService;
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
