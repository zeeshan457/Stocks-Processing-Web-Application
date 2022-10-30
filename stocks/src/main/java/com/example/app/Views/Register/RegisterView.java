package com.example.app.Views.Register;

import com.example.app.Data.Authenticate.AuthService;
import com.example.app.Data.Validation.Validation;
import com.vaadin.flow.component.UI;
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
import lombok.Getter;

import javax.security.auth.message.AuthException;

// Annotations for this class
@AnonymousAllowed
@PageTitle("Register Page")
@Route(value = "Register")
public class RegisterView extends VerticalLayout {

    // Attributes
    private final AuthService service = new AuthService();
    private final Validation validate = new Validation();
    private H2 Title;
    private TextField username;
    private PasswordField password1;
    private PasswordField password2;
    private Button RegisterButton;

    // Constructor and method call
    public RegisterView() {
        AddRegister();
        // Configs
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");

        // RegisterButton action Listener and calling services/validation from specific packages.
        RegisterButton.addClickListener(event -> {
            try {
                if (validate.RegisterValidation(username.getValue(), password1.getValue(), password2.getValue())) {
                    service.Register(username.getValue(), password1.getValue());
                    Notification.show("Registration Success");
                    UI.getCurrent().navigate("Login");
                }
            } catch (AuthException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void AddRegister() {
        Title = new H2("Registration");
        username = new TextField("Username");
        password1 = new PasswordField("Password");
        password2 = new PasswordField("Confirm password");
        RegisterButton = new Button("Register");
        RegisterButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        RegisterButton.setWidth("210px");
        add(Title, username, password1, password2, RegisterButton);
    }
}
