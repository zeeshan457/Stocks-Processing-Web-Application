package com.example.app.Data.Entity;

import com.example.app.Data.Role;
import lombok.*;
import org.hibernate.Hibernate;
import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

// Annotations for this class using Lombok
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity{

    // Entities for the table
    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false, length = 50)
    private Set<Role> roles;

    public User() {
    }
}
