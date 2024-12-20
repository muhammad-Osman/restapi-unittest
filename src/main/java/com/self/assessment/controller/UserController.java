package com.self.assessment.controller;

import com.self.assessment.dto.Post;
import com.self.assessment.model.User;
import com.self.assessment.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<User>> searchUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        Page<User> users;

        if (keyword != null && !keyword.isBlank()) {
            users = userService.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword, pageable);
        } else {
            users = userService.findAll(pageable);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/thirdparty")
    public ResponseEntity<List<Post>> callThirdPartyApi() {
        RestTemplate restTemplate = new RestTemplate();
        Post[] posts = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", Post[].class);
        List<Post> postList = Arrays.asList(posts);
        return ResponseEntity.ok(postList);
    }
}
