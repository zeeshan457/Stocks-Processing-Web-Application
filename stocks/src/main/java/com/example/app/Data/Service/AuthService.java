package com.example.app.Data.Service;

import com.example.app.Data.Entity.UserEntity;
import com.example.app.Data.Repository.UserRepository;
import com.example.app.Data.Role;

import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;

@Service
public class AuthService {

    // Attributes injecting repository here
    @Autowired
    private UserRepository repo;

    public void Login(String username, String password) throws AuthException {
        UserEntity user = repo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            VaadinSession.getCurrent().setAttribute(UserEntity.class, user);
        } else {
            throw new AuthException();
        }
    }

    // Register users here by calling the .save() method
    public void Register(String username, String password) {
        UserEntity user = new UserEntity(username, password, Role.USER);
        repo.save(user);
    }
}
