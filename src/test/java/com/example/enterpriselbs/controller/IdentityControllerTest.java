package com.example.enterpriselbs.controller;

import com.example.enterpriselbs.application.controllers.IdentityController;
import com.example.enterpriselbs.application.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IdentityControllerTest {
    private UserService userService;
    private IdentityController identityController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        identityController = new IdentityController(userService);
    }

    @Test
    @DisplayName("validate returns 200 OK with token when authentication succeeds")
    void testValidate_Success() {
        Map<String, String> loginRequest = Map.of(
                "username", "user1",
                "password", "pass123"
        );

        when(userService.authenticate("user1", "pass123")).thenReturn(Optional.of("token123"));

        ResponseEntity<?> response = identityController.validate(loginRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Map.of("token", "token123"), response.getBody());
    }

    @Test
    @DisplayName("validate returns 401 Unauthorized when authentication fails")
    void testValidate_Failure() {
        Map<String, String> loginRequest = Map.of(
                "username", "user1",
                "password", "wrongpass"
        );

        when(userService.authenticate("user1", "wrongpass")).thenReturn(Optional.empty());

        ResponseEntity<?> response = identityController.validate(loginRequest);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals(Map.of("error", "Invalid credentials"), response.getBody());
    }
}
