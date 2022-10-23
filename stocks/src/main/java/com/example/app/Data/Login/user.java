package com.example.app.Data.Login;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// Annotations for this class
@Getter
@Setter
@Entity
@Table(name = "users")
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String password;
}
