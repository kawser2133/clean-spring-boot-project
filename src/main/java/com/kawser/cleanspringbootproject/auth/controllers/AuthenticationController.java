package com.kawser.cleanspringbootproject.auth.controllers;

import com.kawser.cleanspringbootproject.auth.models.dto.authentication.LoginDTO;
import com.kawser.cleanspringbootproject.auth.models.dto.authentication.LoginResponseDTO;
import com.kawser.cleanspringbootproject.auth.models.dto.authentication.SignupDTO;
import com.kawser.cleanspringbootproject.auth.services.IAuthenticationService;
import com.kawser.cleanspringbootproject.auth.services.impl.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller class for authentication endpoints. It handles the HTTP requests related to authentication.
 * It uses the AuthenticationService to perform operations on the database.
 * 
 * @see AuthenticationService
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final IAuthenticationService authService;

    AuthenticationController(IAuthenticationService authService){
        this.authService= authService;
    }

    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * Authenticate a user with the given credentials and return a token if successful. 
     * @param data The credentials of the user to be authenticated, passed as a request body.
     * @return The response containing the token if the user is authenticated successfully.
     */
    @Operation(summary = "Authenticate a user", description = "Authenticate a user with the given credentials and return a token if successful")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully with the given credentials and a token is returned"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials provided"),
            @ApiResponse(responseCode = "400", description = "User is not verified")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody @Valid LoginDTO data) {
        
        var response = authService.login(data);
        return ResponseEntity.ok(response);
    }

    /**
     * Sign up a new user with the given data and send a verification email.
     * @param data The data of the user to be signed up, passed as a request body.
     * @return The response containing a message indicating that the user was signed up successfully.
     */
    @Operation(summary = "Sign up a new user", description = "Sign up a new user with the given data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User signed up successfully"),
            @ApiResponse(responseCode = "400", description = "Email or username already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @RequestBody @Valid SignupDTO data) {

        authService.signup(data);
        return ResponseEntity.ok(bundle.getString("user.successfully_signed_up"));
    }

    /**
     * Verify a user's email with the given email and token.
     * @param email the email of the user to be verified
     * @param token The token to verify the user's email.
     * @return The response containing a message indicating that the user's email was verified successfully.
     */
    @Operation(summary = "Verify a user's email", description = "Verify a user's email with the given email and token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's email verified successfully"),
            @ApiResponse(responseCode = "400", description = "User already verified"),
            @ApiResponse(responseCode = "401", description = "Invalid token provided"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(
            @RequestParam String email, 
            @RequestParam String token) {
        
        authService.verifyAccount(email, token);
        return ResponseEntity.ok(bundle.getString("user.successfully_verified"));
    }

    /**
     * Resend the verification email to the user with the given email.
     * @param email The email of the user to resend the verification email.
     * @return The response containing a message indicating that the verification email was resent successfully.
     */
    @Operation(summary = "Resend verification email", description = "Resend the verification email to the user with the given email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verification email resent successfully"),
            @ApiResponse(responseCode = "400", description = "User already verified"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/resend-verification")
    public ResponseEntity<String> resendVerification(
            @RequestParam String email) {
        
        authService.resendVerification(email);
        return ResponseEntity.ok(bundle.getString("user.verification_email_resent"));
    }
}
