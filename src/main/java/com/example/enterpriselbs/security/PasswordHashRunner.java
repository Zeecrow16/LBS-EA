package com.example.enterpriselbs.security;

import com.example.enterpriselbs.infrastructure.entities.StaffEntity;
import com.example.enterpriselbs.infrastructure.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHashRunner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public PasswordHashRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        for (StaffEntity staff : userRepository.findAll()) {
            String rawPassword = staff.getPasswordHash();
            staff.setPasswordHash(passwordEncoder.encode(rawPassword));
            userRepository.save(staff);
        }
    }

}
