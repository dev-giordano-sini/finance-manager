package it.financemanager.infrastructure.web.auth;

import it.financemanager.auth.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) { }
