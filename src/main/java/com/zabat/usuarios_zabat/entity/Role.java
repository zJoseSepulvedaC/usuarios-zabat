package com.zabat.usuarios_zabat.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ROLES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true, length = 30)
    private String name;

    @Column(name = "DESCRIPTION", length = 150)
    private String description;

    // Evita recursión infinita con User.roles
    @JsonIgnoreProperties("roles")
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
