package com.zeyza.auth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zeyza.auth.entity.Role;
import com.zeyza.auth.enums.ERole;
import com.zeyza.auth.repository.RoleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService extends BaseService<Role> {

    private final RoleRepository roleRepository;

    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void save(Role entity) {
        roleRepository.save(entity);
    }
}
