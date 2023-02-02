package nz.netvalue.domain.service.impl;

import nz.netvalue.persistence.model.User;
import nz.netvalue.persistence.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("Test service for user details")
@SpringBootTest(classes = UserDetailsServiceImpl.class)
class UserDetailsServiceImplTest {

    public static final String USERNAME = "username";
    @Autowired
    private UserDetailsServiceImpl sut;

    @MockBean
    private UserRepository repository;

    @Test
    @DisplayName("Should return user by username")
    void shouldGetUserByUsername() {
        when(repository.findByUsername(USERNAME)).thenReturn(Optional.of(createUseDetails()));
        UserDetails actual = sut.loadUserByUsername(USERNAME);

        assertEquals(USERNAME, actual.getUsername());
    }

    @Test
    @DisplayName("Should fail if user not found")
    void shouldFailWhenUserNotFound() {
        when(repository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> sut.loadUserByUsername(USERNAME));
    }

    private static User createUseDetails() {
        User user = new User();
        user.setUsername(USERNAME);
        return user;
    }
}