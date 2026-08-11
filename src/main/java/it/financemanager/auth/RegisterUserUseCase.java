package it.financemanager.auth;

public interface RegisterUserUseCase {
    AuthResponse register(RegisterRequest request);
}
