package it.financemanager.application.service;
import it.financemanager.application.exception.ResourceNotFoundException;
import it.financemanager.application.port.out.*;
import it.financemanager.domain.model.User;
final class UserResolver {
    private final CurrentActorPort actor;
    private final UserPort users;
    UserResolver(CurrentActorPort actor, UserPort users) {
        this.actor = actor;
        this.users = users;
    }
    User current() {
        return users.findByEmail(actor.email()).orElseThrow(() -> new ResourceNotFoundException("User", 0L));
    }
}
