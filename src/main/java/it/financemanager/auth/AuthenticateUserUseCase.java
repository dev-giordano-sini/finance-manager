package it.financemanager.auth;

public interface AuthenticateUserUseCase {
    AuthResponse login(LoginRequest request);
}
