package com.zeyza.auth.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zeyza.auth.entity.Role;
import com.zeyza.auth.enums.ERole;
import com.zeyza.auth.service.RoleService;

@Configuration
public class InitRolesConfig {

    @Bean
    public CommandLineRunner initRoles(RoleService roleService) {
        return args -> {
            for (ERole role : ERole.values()) {
                if (roleService.findByName(role).isEmpty()) {
                    Role newRole = new Role();
                    newRole.setName(role);
                    roleService.save(newRole);
                }
            }
        };
    }
}