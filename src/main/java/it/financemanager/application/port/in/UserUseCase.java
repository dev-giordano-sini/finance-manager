package it.financemanager.application.port.in;
public interface UserUseCase { record CurrentUser(String name, String surname, String email) { } CurrentUser current(); }
