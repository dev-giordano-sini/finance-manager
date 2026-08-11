package it.financemanager.user;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    <S extends User> S saveAndFlush(S user);
}
