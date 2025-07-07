package com.talentwave.repository;

import com.talentwave.domain.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Disabled("Docker environment not available in current sandbox")
public class UserRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.5")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setActive(true);
        user.setCreatedAt(Instant.now());

        // Act
        User savedUser = userRepository.save(user);

        // Assert
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testFindByUsername() {
        // Arrange
        User user = new User();
        user.setUsername("findme");
        user.setEmail("findme@example.com");
        user.setPassword("password");
        user.setFirstName("Find");
        user.setLastName("Me");
        user.setActive(true);
        user.setCreatedAt(Instant.now());
        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findByUsername("findme");

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("findme@example.com");
    }

    @Test
    public void testFindByEmail() {
        // Arrange
        User user = new User();
        user.setUsername("emailtest");
        user.setEmail("unique@example.com");
        user.setPassword("password");
        user.setFirstName("Email");
        user.setLastName("Test");
        user.setActive(true);
        user.setCreatedAt(Instant.now());
        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findByEmail("unique@example.com");

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("emailtest");
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        User user = new User();
        user.setUsername("deleteme");
        user.setEmail("delete@example.com");
        user.setPassword("password");
        user.setFirstName("Delete");
        user.setLastName("Me");
        user.setActive(true);
        user.setCreatedAt(Instant.now());
        User savedUser = userRepository.save(user);

        // Act
        userRepository.deleteById(savedUser.getId());
        Optional<User> deletedUser = userRepository.findById(savedUser.getId());

        // Assert
        assertThat(deletedUser).isEmpty();
    }
}
