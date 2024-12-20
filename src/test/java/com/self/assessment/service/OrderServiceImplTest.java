package com.self.assessment.service;

import com.self.assessment.model.Order;
import com.self.assessment.model.User;
import com.self.assessment.repository.*;
import com.self.assessment.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private final OrderRepository orderRepository = mock(OrderRepository.class);
    private final OrderServiceImpl orderService = new OrderServiceImpl(orderRepository);

    @Test
    void shouldGetUserOrders() {
        User user = new User(1L, "John Doe", "john.doe@example.com");
        List<Order> orders = Arrays.asList(
                new Order(1L, "Smartphone", 799.99, user),
                new Order(2L, "Laptop", 1200.00, user)
        );

        when(orderRepository.findAllByUserId(1L)).thenReturn(orders);

        List<Order> result = orderService.getUserOrders(1L);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getItem()).isEqualTo("Smartphone");
        verify(orderRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void shouldCreateOrder() {
        User user = new User(1L, "John Doe", "john.doe@example.com");
        Order order = new Order(1L, "Smartphone", 799.99, user);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertThat(result.getItem()).isEqualTo("Smartphone");
        assertThat(result.getPrice()).isEqualTo(799.99);
        verify(orderRepository, times(1)).save(order);
    }
}