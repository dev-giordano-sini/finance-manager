package it.financemanager.auth;
public interface AuthUseCase { AuthResponse register(RegisterRequest request); AuthResponse login(LoginRequest request); }
