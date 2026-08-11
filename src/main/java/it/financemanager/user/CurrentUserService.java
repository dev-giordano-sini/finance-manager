package it.financemanager.user;

import it.financemanager.common.exception.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import it.financemanager.user.port.out.UserOutputPort;

@Service
public class CurrentUserService {
    private final UserOutputPort repository;
    public CurrentUserService(UserOutputPort repository) { this.repository = repository; }
    public User get() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findByEmailIgnoreCase(email).orElseThrow(() -> new ResourceNotFoundException("User", 0L));
    }

    public CurrentUserResponse getCurrentUser() {
        User currentUser = get();
        return CurrentUserResponse.from(currentUser);
    }

}
