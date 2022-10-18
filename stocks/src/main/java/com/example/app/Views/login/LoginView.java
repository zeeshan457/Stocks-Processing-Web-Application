package com.example.app.Views.login;

import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

// Annotations for the class configurations
@PageTitle("Login Page")
@Route(value = "Login")
public class LoginView extends Composite<LoginOverlay> {

    // Constructor
    public LoginView() {
    LoginOverlay login = getContent();
    login.setTitle("Stocks Application");
    login.setDescription(null);
    login.setForgotPasswordButtonVisible(false);
    login.setOpened(true);

login.addLoginListener(event -> {
    if ("user".equals(event.getUsername()) && "user".equals(event.getPassword())) {
        Notification.show("Login Successful, Welcome.");
        UI.getCurrent().navigate("Home");

    } else {
        Notification.show("Username or Password is incorrect");
    }
});




    }
}
