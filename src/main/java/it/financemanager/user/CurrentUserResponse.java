package it.financemanager.user;

import it.financemanager.infrastructure.persistence.entity.User;

public record CurrentUserResponse(
        String name,
        String surname,
        String email
) {
    public static CurrentUserResponse from(User user) {

        if (user == null) {
            return null;
        }

        return new CurrentUserResponse (
                user.getName(),
                user.getSurname(),
                user.getEmail()
        );
    }
}
