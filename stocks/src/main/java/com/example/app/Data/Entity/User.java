package com.example.app.Data.Entity;

import com.example.app.Data.Role;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// Annotations for this class
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    // Entities for the table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false, length = 50)
    private Set<Role> roles;


    // Non args constructor
    public User() {
    }
}
