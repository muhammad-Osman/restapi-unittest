package com.self.assessment.service.impl;

import com.self.assessment.model.User;
import com.self.assessment.repository.UserRepository;
import com.self.assessment.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String keyword, String email, Pageable pageable) {
        return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, email, pageable);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
