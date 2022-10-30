package com.example.app.Views.Register;

import com.example.app.Data.Authenticate.AuthService;
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

// Annotations for this class
@AnonymousAllowed
@PageTitle("Register Page")
@Route(value = "Register")
@Getter
public class RegisterView extends VerticalLayout {

    // Attributes
    AuthService service = new AuthService();
    private H2 Title;
    private TextField Username;
    private PasswordField Password;
    private PasswordField RepeatPassword;
    private Button RegisterButton;

    // Constructor and method call
    public RegisterView() {
        AddRegister();
        // Configs
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");

        // RegisterButton action Listener
        RegisterButton.addClickListener(event -> {
            service.Register(Username.getValue(), Password.getValue());
            Notification.show("Registration Success");
            UI.getCurrent().navigate("Home");

        });
    }

    public void AddRegister() {
        Title = new H2("Registration");
        Username = new TextField("Username");
        Password = new PasswordField("Password");
        RepeatPassword = new PasswordField("Confirm password");
        RegisterButton = new Button("Register");
        RegisterButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        RegisterButton.setWidth("210px");
        add(Title, Username, Password, RepeatPassword, RegisterButton);
    }
}
