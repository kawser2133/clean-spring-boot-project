package com.kawser.cleanspringbootproject.auth.repositories;

import com.kawser.cleanspringbootproject.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * This interface is a repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long>{

    /**
     * Find a user by username.
     * 
     * @param username The username of the user.
     * @return The UserDetails object of the user.
     */
    UserDetails findByUsername(String username);

    /**
     * Find a user by email.
     * 
     * @param email The email of the user.
     * @return The user object.
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a user with the given username exists in the database.
     * 
     * @param username The username of the user.
     */
    boolean existsByUsername(String username);

    /**
     * Check if a user with the given email exists in the database.
     * 
     * @param email The email of the user.
     */
    boolean existsByEmail(String email);
}
