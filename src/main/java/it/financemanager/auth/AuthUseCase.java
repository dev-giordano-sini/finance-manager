package it.financemanager.auth;

public interface AuthUseCase {
    AuthResponse register(RegisterCommand command);

    AuthResponse login(LoginCommand command);
}
