package com.qlda.config;

import com.qlda.model.Role;
import com.qlda.model.User;
import com.qlda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
//1
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setHoTen("Administrator");
            admin.setEmail("admin@qlda.edu.vn");
            admin.setRole(Role.ROLE_ADMIN);
            admin.setActive(true);
            userRepository.save(admin);
        }


    }
}

