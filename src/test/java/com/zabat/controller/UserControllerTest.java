package com.zabat.controller;

import com.zabat.usuarios_zabat.UsuariosZabatApplication;
import com.zabat.usuarios_zabat.entity.User;
import com.zabat.usuarios_zabat.entity.Role;
import com.zabat.usuarios_zabat.repository.UserRepository;
import com.zabat.usuarios_zabat.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = UsuariosZabatApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    void getAll_ok() throws Exception {
        User user = User.builder()
                .id(1L)
                .username("admin")
                .email("admin@test.com")
                .passwordHash("hashed12345")
                .enabled(1)
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("admin"));
    }

    @Test
    void getById_ok() throws Exception {
        User user = User.builder()
                .id(1L)
                .username("user1")
                .email("user1@test.com")
                .passwordHash("hash12345")
                .enabled(1)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user1"));
    }

    @Test
    void create_conflict_username() throws Exception {
        when(userRepository.existsByUsername("admin")).thenReturn(true);

        String body = """
            {
              "username": "admin",
              "email": "admin@test.com",
              "passwordHash": "password123"
            }
        """;

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }

@Test
void assignRole_ok() throws Exception {
    User user = User.builder()
            .id(1L)
            .username("user1")
            .roles(new java.util.HashSet<>())
            .build();


        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenReturn(user);

        mockMvc.perform(post("/users/1/roles/ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles[0].name").value("ADMIN"));
    }
}
