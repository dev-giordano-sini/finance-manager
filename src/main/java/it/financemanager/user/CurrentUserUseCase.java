package it.financemanager.user;

public interface CurrentUserUseCase {
    User get();

    CurrentUserResponse getCurrentUser();
}
