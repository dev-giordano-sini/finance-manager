package it.financemanager.role;

public enum BaseRole {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String role;

    BaseRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
