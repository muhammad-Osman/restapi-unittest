package com.self.assessment.service;

import com.self.assessment.model.Order;

import java.util.List;

public interface OrderService {
    public List<Order> getUserOrders(Long userId);
    public Order createOrder(Order order);
}
