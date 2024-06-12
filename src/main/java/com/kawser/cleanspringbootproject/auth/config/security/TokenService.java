package com.kawser.cleanspringbootproject.auth.config.security;

import com.kawser.cleanspringbootproject.auth.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * This service is responsible for handling the token operations.
 * 
 */
@Service
@Slf4j
public class TokenService {

    /**
     * Secret used to generate the token, fetched from application.properties.
     */
    @Value("${auth.security.token.secret}")
    private String secret;

    /**
     * Token expiration time, fetched from application.properties
     */
    @Value("${auth.security.token.expiration-time}")
    private long EXPIRATION_TIME;

    /**
     * Generates a token for the given user with the expiration time defined in the application.properties.
     * @param user User to generate the token
     * @throws RuntimeException Exception thrown in case of error while generating the token
     * @return Token generated
     */
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var now = Instant.now();
            
            String token = JWT.create()
                    .withIssuer("auth-service")
                    .withSubject(user.getUsername())
                    .withExpiresAt(now.plusSeconds(EXPIRATION_TIME))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            log.error("Error while generating token");
            throw new RuntimeException("Error while generating token");
        }
    }

    /**
     * Validates the token and returns the decoded subject.
     * @param token Token to validate
     * @throws JWTVerificationException Exception thrown in case of error while validating the token
     * @return Subject of the token
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            var verifier = JWT.require(algorithm)
                    .withIssuer("auth-service")
                    .build();
            var decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

}

