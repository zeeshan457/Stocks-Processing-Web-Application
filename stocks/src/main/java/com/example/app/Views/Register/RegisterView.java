package com.example.app.Views.Register;

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

@AnonymousAllowed
@PageTitle("Register Page")
@Route(value = "Register")
public class RegisterView extends VerticalLayout {

    /**
     *
     * Attributes and injecting
     *
     */
    @Autowired
    private AuthService service;
    private final Validation validate = new Validation();
    private H2 Title;
    private TextField username;
    private PasswordField password1;
    private PasswordField password2;
    private Button RegisterButton;
    private Button BackButton;
    HorizontalLayout layout = new HorizontalLayout();

    /**
     *
     * Constructor and method calls
     *
     */
    public RegisterView() {
        AddRegister();
        actionEvents();
    }


    /**
     *
     * RegisterButton action Listener and calling services/validation from specific packages.
     * Action events
     *
     */
    public void actionEvents() {
        RegisterButton.addClickListener(event -> {
            if (validate.RegisterValidation(username.getValue(), password1.getValue(), password2.getValue())) {
                service.Register(username.getValue(), password1.getValue());
            }
        });
        BackButton.addClickListener(event -> {
            UI.getCurrent().navigate("Menu");
        });
    }

    /**
     *
     * creating the register fields
     *
     */
    public void AddRegister() {
        Title = new H2("Register");
        username = new TextField("Username");
        password1 = new PasswordField("Password");
        password2 = new PasswordField("Confirm password");
        BackButton = new Button("Back", VaadinIcon.ARROW_BACKWARD.create());
        RegisterButton = new Button("Register", VaadinIcon.SIGN_IN.create());
        RegisterButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        RegisterButton.setWidth("115px");
        BackButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        BackButton.setWidth("80px");
        layout.add(BackButton, RegisterButton);
        add(Title, username, password1, password2, layout);
        // Configs
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
