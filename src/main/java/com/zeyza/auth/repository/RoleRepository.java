package com.zeyza.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zeyza.auth.entity.Role;
import com.zeyza.auth.enums.ERole;

public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByName(ERole name);
}
