package com.zeyza.auth.config;

import java.util.HashSet;
import java.util.Set;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.zeyza.auth.entity.Role;
import com.zeyza.auth.entity.User;
import com.zeyza.auth.enums.ERole;
import com.zeyza.auth.service.UserService;

@Component
public class DatabaseMigrationRunner implements CommandLineRunner {

    @Autowired
    Flyway flyway;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userService.existsByUsername("admin")) {
            Set<Role> roles = new HashSet<>();

            roles.add(new Role(ERole.ADMIN));

            User user = new User();
            user.setEmail("everythingwithzeyza@gmail.com");
            user.setUsername("admin");
            user.setPassword(encoder.encode("password"));
            user.setIsEmailVerified(true);
            user.setRoles(roles);

            userService.save(user);
        }
        flyway.migrate();
    }

}
