package it.financemanager.application.port.in;
public interface AuthUseCase {
 record Token(String accessToken, String tokenType, long expiresIn) { }
 Token register(String name, String surname, String email, String password); Token login(String email, String password);
}
