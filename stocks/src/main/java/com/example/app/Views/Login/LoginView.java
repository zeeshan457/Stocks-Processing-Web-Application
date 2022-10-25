package com.example.app.Views.Login;

import com.example.app.Data.Login.LoginService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

// Annotations for the class configurations
@PageTitle("Login Page")
@Route(value = "Login")
public class LoginView extends Composite<LoginOverlay> {

    // Attributes
    String username;
    String password;

    // Constructor calling method here
    public LoginView() {
        AddLogin();
    }

    public void AddLogin() {
        LoginOverlay login = getContent();
        login.setTitle("Stocks Application");
        login.setDescription(null);
        login.setForgotPasswordButtonVisible(false);
        login.setOpened(true);

        // Login listener
        login.addLoginListener(event -> {


        });
    }
}
