package com.self.assessment.service;

import com.self.assessment.model.User;
import com.self.assessment.repository.UserRepository;
import com.self.assessment.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserServiceImpl userService = new UserServiceImpl(userRepository);

    @Test
    void shouldGetAllUsers() {
        List<User> users = Arrays.asList(new User(1L, "John Doe", "john.doe@example.com"),
                new User(2L, "Jane Doe", "jane.doe@example.com"));

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertThat(result).hasSize(2);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldCreateUser() {
        User user = new User(1L, "John Doe", "john.doe@example.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.createUser(user);

        assertThat(result.getName()).isEqualTo("John Doe");
        assertThat(result.getEmail()).isEqualTo("john.doe@example.com");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldFindUserById() {
        User user = new User(1L, "John Doe", "john.doe@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("John Doe");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldSearchUsersByKeyword() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> users = new PageImpl<>(List.of(new User(1L, "John Doe", "john.doe@example.com")));

        when(userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase("john", "john", pageable))
                .thenReturn(users);

        Page<User> result = userService.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase("john", "john", pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("John Doe");
        verify(userRepository, times(1))
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase("john", "john", pageable);
    }

    @Test
    void shouldFindAllUsersWithPagination() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> users = new PageImpl<>(List.of(new User(1L, "John Doe", "john.doe@example.com")));

        when(userRepository.findAll(pageable)).thenReturn(users);

        Page<User> result = userService.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("John Doe");
        verify(userRepository, times(1)).findAll(pageable);
    }
}