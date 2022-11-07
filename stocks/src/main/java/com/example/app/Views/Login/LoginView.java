package com.example.app.Views.Login;

import com.example.app.Data.Service.AuthService;
import com.example.app.Data.Validation.Validation;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
    private Button BackButton;
    HorizontalLayout layout = new HorizontalLayout();

    // Constructor and method calls
    public LoginView() {
        AddLogin();
        actionEvents();
    }

    // LoginButton action Listener calling service method and validation here
    public void actionEvents() {
        LoginButton.addClickListener(event -> {
            if (validate.LoginValidation(username.getValue(), password.getValue())) {
                try {
                    service.Login(username.getValue(), password.getValue());
                    Notification loginMessage = Notification.show("Login Success, welcome "
                            + username.getValue());
                    loginMessage.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    UI.getCurrent().navigate("Home");

                } catch(AuthException e){
                    Notification error = Notification.show("Incorrect Login Credentials");
                    error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }
        });
        BackButton.addClickListener(event -> {
            UI.getCurrent().navigate("Menu");
        });
    }

    // creating the login fields here
    public void AddLogin() {
        Title = new H2("Login");
        username = new TextField("Username");
        password = new PasswordField("Password");
        BackButton = new Button("Back", VaadinIcon.ARROW_BACKWARD.create());
        LoginButton = new Button("Login", VaadinIcon.SIGN_IN.create());
        LoginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        LoginButton.setWidth("100px");
        BackButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        BackButton.setWidth("100px");
        layout.add(BackButton, LoginButton);
        add(Title, username, password, layout);
        // Configs
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");
        }
    }
