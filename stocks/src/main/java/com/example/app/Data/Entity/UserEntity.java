package com.example.app.Data.Entity;

import com.example.app.Data.Role;
import lombok.*;
import javax.persistence.*;

// Annotations for this class using Lombok
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")

    /**
     *
     * Creating table for users
     * Column 1 is username
     * Column 2 is password
     * Column 3 is role
     *
     */
public class UserEntity extends AbstractEntity{
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Role roles;
}


