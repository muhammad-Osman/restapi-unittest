package com.self.assessment.controller;

import com.self.assessment.model.Order;
import com.self.assessment.model.User;
import com.self.assessment.service.OrderService;
import com.self.assessment.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserService userService;

    @Test
    public void shouldCreateOrderForExistingUser() throws Exception {
        User user = new User(1L, "John Doe", "john.doe@example.com");
        Order order = new Order(null, "Smartphone", 799.99, user);

        Mockito.when(userService.findById(anyLong())).thenReturn(Optional.of(user));
        Mockito.doNothing().when(orderService).createOrder(any(Order.class));

        mockMvc.perform(post("/api/order?userId=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"item\":\"Smartphone\",\"price\":799.99}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Order created successfully for user ID 1"));
    }

    @Test
    public void shouldReturnNotFoundForNonExistingUser() throws Exception {
        Mockito.when(userService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/order?userId=999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"item\":\"Laptop\",\"price\":999.99}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with ID 999 not found."));
    }
}
