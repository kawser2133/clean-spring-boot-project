package com.kawser.cleanspringbootproject.auth.models.dto.authentication;

/**
 * It is a DTO that represents the data needed to authenticate a user, contains the username and the password.
 * It is used to transfer data between the controller and the service.
 * @see LoginResponseDTO
 */
public record LoginDTO(String username, String password) {
    
}
