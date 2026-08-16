package it.financemanager.infrastructure.web.category;

import it.financemanager.category.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
    @NotBlank @Size(max = 80) String name,
    @NotBlank @Pattern(regexp = "^#[0-9A-Fa-f]{6}$",
                       message = "must be a hexadecimal color such as #1A2B3C")
    String color) {}
