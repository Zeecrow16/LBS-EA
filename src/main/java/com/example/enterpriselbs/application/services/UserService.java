package com.example.enterpriselbs.application.services;

import com.example.enterpriselbs.infrastructure.entities.StaffEntity;
import com.example.enterpriselbs.infrastructure.repositories.UserRepository;
import com.example.enterpriselbs.security.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    public Optional<String> authenticate(String username, String password) {
        Optional<StaffEntity> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent() &&
                passwordEncoder.matches(password, userOpt.get().getPasswordHash())) {

            StaffEntity user = userOpt.get();
            String token = jwtTokenUtil.generateToken(user);

            return Optional.of(token);
        }

        return Optional.empty();
    }
}