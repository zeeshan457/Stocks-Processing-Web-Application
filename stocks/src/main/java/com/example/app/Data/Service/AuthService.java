package com.example.app.Data.Service;

import com.example.app.Data.Entity.UserEntity;
import com.example.app.Data.Repository.UserRepository;
import com.example.app.Data.Role;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;

@Service
public class AuthService {

    // injecting repository here
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

    // Register users here by calling the .save() method and checking if a username already exists
    public void Register(String username, String password) {
        UserEntity findUser = repo.findByUsername(username);
        if (findUser != null && findUser.getUsername().equals(username)) {
            Notification message = Notification.show("Username was taken");
            message.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else {
            UserEntity user = new UserEntity(username, password, Role.USER);
            repo.save(user);
            Notification registerMessage = Notification.show("Registration Success");
            registerMessage.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            UI.getCurrent().navigate("Login");
        }
    }
}
