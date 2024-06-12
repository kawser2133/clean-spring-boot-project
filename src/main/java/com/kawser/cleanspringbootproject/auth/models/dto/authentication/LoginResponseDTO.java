package com.kawser.cleanspringbootproject.auth.models.dto.authentication;

/**
 * It is a DTO that represents the response of a login request, contains the token of the authenticated user.
 * It is used to transfer data between the controller and the service.
 * @see LoginDTO
 */
public record LoginResponseDTO(String token) {
    
}
