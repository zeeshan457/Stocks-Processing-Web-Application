package com.example.app.Views.Login;

import com.example.app.Data.Entity.UserEntity;
import com.example.app.Data.Repository.UserRepository;
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
import org.springframework.beans.factory.annotation.Value;

import javax.security.auth.message.AuthException;

@AnonymousAllowed
@PageTitle("Login Page")
@Route(value = "Login")
public class LoginView extends VerticalLayout {

    /**
     *
     * Attributes and injections
     *
     */
    @Autowired
    private AuthService service;
    private UserRepository repo;
    private final Validation validate = new Validation();
    private H2 Title;
    private TextField username;
    private PasswordField password;
    private Button LoginButton;
    private Button BackButton;
    HorizontalLayout layout = new HorizontalLayout();

    /**
     *
     * Constructing the view
     *
     * @param service injecting here
     */
    public LoginView(@Autowired AuthService service) {
        this.service = service;
        AddLogin();
        actionEvents();
    }

    /**
     *
     * LoginButton action Listener calling service method and validation here
     *
     */
    public void actionEvents() {
        LoginButton.addClickListener(event -> {
            if (validate.LoginValidation(username.getValue(), password.getValue())) {
                try {
                    service.Login(username.getValue(), password.getValue());
                    Notification loginMessage = Notification.show("Login Success, welcome "
                            + username.getValue());
                    loginMessage.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
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

    /**
     *
     * Creating the login fields here
     */
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
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");
        }

    /**
     *
     * @return username field
     */
    public TextField getUsernameTextField() {
        return username;
    }
}
