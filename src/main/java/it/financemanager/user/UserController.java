package it.financemanager.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final CurrentUserUseCase currentUser;

    public UserController(CurrentUserUseCase currentUser) {
        this.currentUser = currentUser;
    }

    @GetMapping("/me")
    CurrentUserResponse getCurrentUser() {
        return currentUser.getCurrentUser();
    }
}
