package com.example.app.Views.Login;

import com.example.app.Data.Authenticate.AuthService;
import com.example.app.Data.Login.UserRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import javax.security.auth.message.AuthException;

// Annotations for the class configurations
@AnonymousAllowed
@PageTitle("Login Page")
@Route(value = "Login")
public class LoginView extends VerticalLayout {

    // Attributes
    AuthService service;
    private H2 Title;
    private TextField username;
    private PasswordField password;
    private Button LoginButton;
    UserRepository repo;

    // Constructor calling method here
    public LoginView() {
        AddLogin();
        // Configs
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");

        // LoginButton action Listener
        LoginButton.addClickListener(event -> {
            try {
                service.Login(username.getValue(), password.getValue());
            } catch (AuthException e) {
                Notification.show("Incorrect Login Credentials");
            }
        });
    }

    public void AddLogin() {
        Title = new H2("Login here");
        username = new TextField("Username");
        password = new PasswordField("Password");
        LoginButton = new Button("login");
        LoginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        LoginButton.setWidth("210px");
        add(Title, username, password, LoginButton);
        }
    }
