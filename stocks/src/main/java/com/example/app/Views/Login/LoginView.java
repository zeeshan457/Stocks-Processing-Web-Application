package com.example.app.Views.Login;

import com.example.app.Data.Authenticate.AuthService;
import com.example.app.Data.Repository.UserRepository;
import com.example.app.Data.Validation.Validation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.message.AuthException;

// Annotations for the class configurations
@AnonymousAllowed
@PageTitle("Login Page")
@Route(value = "Login")
public class LoginView extends VerticalLayout {

    // Attributes
    @Autowired
    private AuthService service;

    private final Validation validate = new Validation();
    private H2 Title;
    private TextField username;
    private PasswordField password;
    private Button LoginButton;

    // Constructor calling method here
    public LoginView() {
        AddLogin();
        // Configs
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");

        // LoginButton action Listener calling service method and validation here
        LoginButton.addClickListener(event -> {
            if (validate.LoginValidation(username.getValue(), password.getValue())) {
                try {
                    service.Login(username.getValue(), password.getValue());
                } catch(AuthException e){
                    Notification error = Notification.show("Incorrect Login Credentials");
                    error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }
        });
    }

    public void AddLogin() {
        Title = new H2("Login");
        username = new TextField("Username");
        password = new PasswordField("Password");
        LoginButton = new Button("login");
        LoginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        LoginButton.setWidth("210px");
        add(Title, username, password, LoginButton);
        }
    }
