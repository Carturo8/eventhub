package com.carturo.eventhub.application.usecase.auth;

import com.carturo.eventhub.domain.model.user.User;
import com.carturo.eventhub.domain.ports.out.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseImplTest {

    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterUserUseCaseImpl registerUserUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(null, "testuser", "rawpassword", null);
    }

    @Test
    void registerUser_SuccessfullyWithDefaultRole() {
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode("rawpassword")).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        User registeredUser = registerUserUseCase.register(user);

        assertNotNull(registeredUser);
        assertEquals(1L, registeredUser.getId());
        assertEquals("testuser", registeredUser.getUsername());
        assertEquals(encodedPassword, registeredUser.getPassword());
        assertTrue(registeredUser.getRoles().contains("USER"));
        assertEquals(1, registeredUser.getRoles().size());

        verify(passwordEncoder, times(1)).encode("rawpassword");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_SuccessfullyWithProvidedRoles() {
        Set<String> roles = new HashSet<>(Collections.singletonList("ADMIN"));
        user.setRoles(roles);
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode("rawpassword")).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        User registeredUser = registerUserUseCase.register(user);

        assertNotNull(registeredUser);
        assertEquals(1L, registeredUser.getId());
        assertEquals("testuser", registeredUser.getUsername());
        assertEquals(encodedPassword, registeredUser.getPassword());
        assertTrue(registeredUser.getRoles().contains("ADMIN"));
        assertEquals(1, registeredUser.getRoles().size());

        verify(passwordEncoder, times(1)).encode("rawpassword");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_WithNullUser_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> registerUserUseCase.register(null));
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void registerUser_WithNullPassword_ThrowsExceptionFromEncoder() {
        user.setPassword(null);
        when(passwordEncoder.encode(null)).thenThrow(new IllegalArgumentException("Password cannot be null"));

        assertThrows(IllegalArgumentException.class, () -> registerUserUseCase.register(user));

        verify(passwordEncoder, times(1)).encode(null);
        verify(userRepository, never()).save(any(User.class));
    }
}