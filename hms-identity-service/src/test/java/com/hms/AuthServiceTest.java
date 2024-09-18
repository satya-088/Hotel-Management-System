package com.hms;


import com.hms.entity.UserCredential;
import com.hms.exception.UserException;
import com.hms.repo.UserCredentialRepository;
import com.hms.service.AuthService;
import com.hms.service.JwtService;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserCredentialRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    private UserCredential userCredential;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userCredential = new UserCredential();
        userCredential.setUsername("testUser");
        userCredential.setPassword("password");
    }

    @Test
    void testSaveUser_UserDoesNotExist() throws UserException {
        when(repository.findByUsername(userCredential.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userCredential.getPassword())).thenReturn("encodedPassword");
        when(repository.save(any(UserCredential.class))).thenReturn(userCredential);

        String result = authService.saveUser(userCredential);

        assertEquals("User added to the system", result);
        verify(repository).save(userCredential);
    }

    @Test
    void testSaveUser_UserAlreadyExists() {
        when(repository.findByUsername(userCredential.getUsername())).thenReturn(Optional.of(userCredential));

        UserException exception = assertThrows(UserException.class, () -> authService.saveUser(userCredential));
        assertEquals("User already exists with the same username", exception.getMessage());
    }

    @Test
    void testGenerateToken_UserFoundAndPasswordMatches() throws UserException {
        when(repository.findByUsername(userCredential.getUsername())).thenReturn(Optional.of(userCredential));
        when(passwordEncoder.matches(userCredential.getPassword(), userCredential.getPassword())).thenReturn(true);
        when(jwtService.generateToken(userCredential.getUsername())).thenReturn("jwtToken");

        String token = authService.generateToken(userCredential.getUsername(), userCredential.getPassword());

        assertEquals("jwtToken", token);
    }

    @Test
    void testGenerateToken_UserNotFound() {
        when(repository.findByUsername(userCredential.getUsername())).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> authService.generateToken(userCredential.getUsername(), userCredential.getPassword()));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGenerateToken_InvalidPassword() {
        when(repository.findByUsername(userCredential.getUsername())).thenReturn(Optional.of(userCredential));
        when(passwordEncoder.matches(userCredential.getPassword(), userCredential.getPassword())).thenReturn(false);

        UserException exception = assertThrows(UserException.class, () -> authService.generateToken(userCredential.getUsername(), userCredential.getPassword()));
        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    void testValidateToken_ValidToken() throws UserException {
        String token = "validToken";
        doNothing().when(jwtService).validateToken(token);

        assertDoesNotThrow(() -> authService.validateToken(token));
    }

    @Test
    void testValidateToken_InvalidToken() {
        String token = "invalidToken";
        doThrow(new JwtException("Invalid token")).when(jwtService).validateToken(token);

        UserException exception = assertThrows(UserException.class, () -> authService.validateToken(token));
        assertEquals("Invalid token", exception.getMessage());
    }
}
