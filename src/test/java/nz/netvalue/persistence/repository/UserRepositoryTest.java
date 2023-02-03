package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DisplayName("Test repository works with users")
class UserRepositoryTest {

    @Autowired
    private UserRepository sut;

    @Test
    @DisplayName("Should return existing user")
    void shouldReturnExistingUser() {
        Optional<User> actual = sut.findByUsername("admin");

        assertTrue(actual.isPresent());
    }

    @Test
    @DisplayName("Should return empty if user not found")
    void shouldReturnEmptyWhenUserNotFound() {
        Optional<User> actual = sut.findByUsername("admin1");

        assertTrue(actual.isEmpty());
    }
}