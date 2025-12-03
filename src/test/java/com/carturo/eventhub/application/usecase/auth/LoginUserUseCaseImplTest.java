package com.carturo.eventhub.application.usecase.auth;

import com.carturo.eventhub.infrastructure.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUserUseCaseImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private LoginUserUseCaseImpl loginUserUseCase;

    private String username;
    private String password;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        username = "testuser";
        password = "testpassword";
        userDetails = new User(username, password, Collections.emptyList());
    }

    @Test
    void login_Successfully() {
        String expectedToken = "mockedJwtToken";
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtil.generateToken(eq(username), any(HashSet.class))).thenReturn(expectedToken);

        String token = loginUserUseCase.login(username, password);

        assertNotNull(token);
        assertEquals(expectedToken, token);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, times(1)).generateToken(eq(username), any(HashSet.class));
    }

    @Test
    void login_WhenBadCredentials_ThrowsBadCredentialsException() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(BadCredentialsException.class, () -> loginUserUseCase.login(username, password));

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, never()).generateToken(anyString(), any(HashSet.class));
    }

    @Test
    void login_WithNullUsername_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> loginUserUseCase.login(null, password));
        verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, never()).generateToken(anyString(), any(HashSet.class));
    }

    @Test
    void login_WithEmptyUsername_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> loginUserUseCase.login("", password));
        verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, never()).generateToken(anyString(), any(HashSet.class));
    }

    @Test
    void login_WithNullPassword_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> loginUserUseCase.login(username, null));
        verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, never()).generateToken(anyString(), any(HashSet.class));
    }

    @Test
    void login_WithEmptyPassword_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> loginUserUseCase.login(username, ""));
        verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtil, never()).generateToken(anyString(), any(HashSet.class));
    }
}