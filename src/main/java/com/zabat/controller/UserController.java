package com.zabat.controller;


import com.zabat.usuarios_zabat.entity.User;
import com.zabat.usuarios_zabat.repository.UserRepository;
import com.zabat.usuarios_zabat.repository.RoleRepository;
import com.zabat.usuarios_zabat.entity.Role;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // Obtener todos los usuarios
    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear usuario
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        user.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User updated) {
        return userRepository.findById(id)
                .map(existing -> {
                    existing.setEmail(updated.getEmail());
                    existing.setEnabled(updated.getEnabled());
                    existing.setPasswordHash(updated.getPasswordHash());
                    existing.setRoles(updated.getRoles());
                    return ResponseEntity.ok(userRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!userRepository.existsById(id)) return ResponseEntity.notFound().build();
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Asignar rol a usuario
    @PostMapping("/{id}/roles/{roleName}")
    public ResponseEntity<User> assignRole(@PathVariable Long id, @PathVariable String roleName) {
        Optional<User> userOpt = userRepository.findById(id);
        Optional<Role> roleOpt = roleRepository.findByName(roleName);

        if (userOpt.isPresent() && roleOpt.isPresent()) {
            User user = userOpt.get();
            Role role = roleOpt.get();
            user.getRoles().add(role);
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
}
