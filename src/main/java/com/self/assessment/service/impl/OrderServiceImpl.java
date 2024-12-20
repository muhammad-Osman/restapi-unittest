package com.self.assessment.service.impl;

import com.self.assessment.model.Order;
import com.self.assessment.repository.OrderRepository;
import com.self.assessment.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }
}
