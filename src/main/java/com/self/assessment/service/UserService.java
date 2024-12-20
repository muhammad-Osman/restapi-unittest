package com.self.assessment.service;

import com.self.assessment.model.Order;
import com.self.assessment.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
    Optional<User> findById(Long id);
    Page<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String keyword, String email, Pageable pageable);
    Page<User> findAll(Pageable pageable);
}
