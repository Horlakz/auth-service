package com.zeyza.auth.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.zeyza.auth.entity.User;
import com.zeyza.auth.exception.EntityNotFoundException;
import com.zeyza.auth.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService extends BaseService<User> {
    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByEmail(String email) throws EntityNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException());
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    public Boolean userIsVerified(String email) throws EntityNotFoundException {
        User user = findByEmail(email);

        return user.getIsEmailVerified();
    }
}
