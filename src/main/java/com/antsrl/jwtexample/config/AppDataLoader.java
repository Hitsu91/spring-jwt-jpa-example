package com.antsrl.jwtexample.config;

import com.antsrl.jwtexample.entity.Role;
import com.antsrl.jwtexample.entity.User;
import com.antsrl.jwtexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        seedUsers();
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            log.info("Creating Admin");
            var admin = User.builder()
                    .username("admin")
                    .password(encoder.encode("admin"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
        }
    }
}
