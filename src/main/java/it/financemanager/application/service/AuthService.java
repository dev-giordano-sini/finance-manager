package it.financemanager.application.service;
import it.financemanager.application.exception.*; import it.financemanager.application.port.in.AuthUseCase; import it.financemanager.application.port.out.*; import it.financemanager.domain.model.User; import java.util.Locale;
public final class AuthService implements AuthUseCase {
 private final UserPort users; private final PasswordPort passwords; private final TokenPort tokens;
 public AuthService(UserPort users,PasswordPort passwords,TokenPort tokens){this.users=users;this.passwords=passwords;this.tokens=tokens;}
 public Token register(String name,String surname,String email,String password){String normalized=normalize(email);if(users.existsByEmail(normalized))throw new ConflictException("An account with this email already exists");users.create(normalized,passwords.encode(password),name.trim(),surname.trim(),"USER");return token(normalized);}
 public Token login(String email,String password){String normalized=normalize(email);User user=users.findByEmail(normalized).orElseThrow(InvalidCredentialsException::new);if(!passwords.matches(password,user.passwordHash()))throw new InvalidCredentialsException();return token(normalized);}
 private Token token(String email){return new Token(tokens.generate(email),"Bearer",tokens.expiresInSeconds());} private String normalize(String email){return email.trim().toLowerCase(Locale.ROOT);}
}
