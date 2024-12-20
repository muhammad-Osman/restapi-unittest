package com.self.assessment.controller;

import com.self.assessment.model.User;
import com.self.assessment.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void shouldGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(new User(1L, "John Doe", "john.doe@example.com"),
                                         new User(2L, "Jane Doe", "jane.doe@example.com"));

        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("John Doe")))
                .andExpect(jsonPath("$[1].email", is("jane.doe@example.com")));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        User user = new User(1L, "John Doe", "john.doe@example.com");

        Mockito.when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("John Doe")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")));
    }

    @Test
    public void shouldSearchUsers() throws Exception {
        Page<User> users = new PageImpl<>(List.of(new User(1L, "John Doe", "john.doe@example.com")));
        Mockito.when(userService.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(anyString(), anyString(), any(Pageable.class)))
               .thenReturn(users);

        mockMvc.perform(get("/api/users/search?keyword=john&page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name", is("John Doe")));
    }

    @Test
    public void shouldCallThirdPartyApi() throws Exception {
        mockMvc.perform(get("/api/users/thirdparty"))
                .andExpect(status().isOk());
    }
}
