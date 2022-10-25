package com.example.app.Data.Login;

import com.example.app.Data.Entity.User;
import com.vaadin.flow.component.page.Page;
import org.springframework.data.jpa.repository.JpaRepository;


// gain access to the database with this interface
public interface UserRepository extends JpaRepository<User, Long> {

    User getByUsername(String username);
}
