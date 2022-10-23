package com.example.app.Data.Login;

import com.example.app.Data.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// gain access to the database with this interface
public interface UserRepository extends JpaRepository<User, Integer> {
    User getByUsername(String username);

}
