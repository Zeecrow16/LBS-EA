package com.example.enterpriselbs.application.controllers;

import com.example.enterpriselbs.application.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("login")
public class IdentityController {

    private final UserService userService;
    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        return userService.authenticate(username, password)
                .<ResponseEntity<?>>map(token -> ResponseEntity.ok(Map.of("token", token)))
                .orElse(ResponseEntity.status(401).body(Map.of("error", "Invalid credentials")));
    }
}
