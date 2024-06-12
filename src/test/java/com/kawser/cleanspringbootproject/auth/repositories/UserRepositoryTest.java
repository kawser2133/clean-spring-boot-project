package com.kawser.cleanspringbootproject.auth.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.kawser.cleanspringbootproject.auth.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.userdetails.UserDetails;
import com.github.javafaker.Faker;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private Faker faker = new Faker();

    private User user;

    @BeforeEach
    public void setUp() {
        entityManager.clear();

        user = User.builder()
                .username(faker.lorem().characters(5, 15))
                .email(faker.internet().emailAddress())
                .password(faker.lorem().characters(10, 15) + "A1#")
                .build();
    }

    @Test
    public void whenFindByLogin_thenReturnUser() {
        User savedUser = entityManager.persistAndFlush(user);

        UserDetails foundUser = userRepository.findByUsername(savedUser.getUsername());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo(savedUser.getUsername());
    }

    @Test
    public void whenNotFindByLogin_thenReturnNull() {
        UserDetails foundUser = userRepository.findByUsername(faker.name().username());

        assertThat(foundUser).isNull();
    }

    @Test
    public void whenFindByEmail_thenReturnUser() {
        User savedUser = entityManager.persistAndFlush(user);

        Optional<User> foundUser = userRepository.findByEmail(savedUser.getEmail());

        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    public void whenNotFindByEmail_thenReturnEmpty() {
        Optional<User> foundUser = userRepository.findByEmail(faker.internet().emailAddress());

        assertThat(foundUser.isEmpty()).isTrue();
    }

    @Test
    public void whenExistsByLogin_thenReturnTrue() {
        User savedUser = entityManager.persistAndFlush(user);

        boolean exists = userRepository.existsByUsername(savedUser.getUsername());

        assertThat(exists).isTrue();
    }

    @Test
    public void whenNotExistsByLogin_thenReturnFalse() {
        boolean exists = userRepository.existsByUsername(faker.name().username());

        assertThat(exists).isFalse();
    }

    @Test
    public void whenExistsByEmail_thenReturnTrue() {
        User savedUser = entityManager.persistAndFlush(user);

        boolean exists = userRepository.existsByEmail(savedUser.getEmail());

        assertThat(exists).isTrue();
    }

    @Test
    public void whenNotExistsByEmail_thenReturnFalse() {
        boolean exists = userRepository.existsByEmail(faker.internet().emailAddress());

        assertThat(exists).isFalse();
    }
}
