package com.self.assessment.controller;

import com.self.assessment.model.Order;
import com.self.assessment.model.User;
import com.self.assessment.service.OrderService;
import com.self.assessment.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestParam Long userId, @RequestBody Order order) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with ID " + userId + " not found.");
        }

        User user = userOptional.get();
        order.setUser(user);

        orderService.createOrder(order);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Order created successfully for user ID " + userId);
    }

}
