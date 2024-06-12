package com.kawser.cleanspringbootproject.auth.config.security;

import com.kawser.cleanspringbootproject.auth.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter responsible to intercept the requests and apply the security rules.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {
    
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    /**
     * This method is called for each request and is responsible for applying the security rules.
     * The token is recovered from the request and validaded using the TokenService, then the user is recovered from the database and set in the SecurityContext.
     * @param request Request to be filtered
     * @param response Response to be filtered
     * @param filterChain Filter chain to be applied
     * @throws ServletException Exception thrown in case of error
     * @throws IOException Exception thrown in case of error
     * @see TokenService
     * @see UserRepository
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = recoverToken(request);

        if (token != null) {
            var login = tokenService.validateToken(token);

            UserDetails user = userRepository.findByUsername(login);

            // Create an authentication token and set it in the SecurityContext
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Method responsible for recovering the token from the request.
     * @param request Request to recover the token
     * @return Token recovered or null if the header is null, empty or does not start with "Bearer "
     */
    private String recoverToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.replace("Bearer ", "");
    }

}
