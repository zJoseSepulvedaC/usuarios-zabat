package com.zabat.usuarios_zabat.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.zabat.usuarios_zabat.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
