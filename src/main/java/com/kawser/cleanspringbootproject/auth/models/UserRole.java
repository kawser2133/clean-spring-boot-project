package com.kawser.cleanspringbootproject.auth.models;

/**
 * Enum to represent the user role.
 * Contains the roles: admin and user.
 */
public enum UserRole {
    
    ADMIN("admin"),
    USER("user");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
