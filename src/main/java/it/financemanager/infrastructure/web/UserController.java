package it.financemanager.infrastructure.web;
import it.financemanager.application.port.in.UserUseCase;
import it.financemanager.infrastructure.web.dto.ApiDtos.CurrentUserResponse;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserUseCase u;
    public UserController(UserUseCase u) {
        this.u = u;
    }
    @GetMapping("/me")
    CurrentUserResponse current() {
        var x = u.current();
        return new CurrentUserResponse(x.name(), x.surname(), x.email());
    }
}
