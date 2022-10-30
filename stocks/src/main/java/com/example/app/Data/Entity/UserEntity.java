package com.example.app.Data.Entity;

import com.example.app.Data.Role;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

// Annotations for this class using Lombok
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity extends AbstractEntity{

    // Entities for the table
    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Role roles;

}


