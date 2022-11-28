package com.example.app.Views.Menu;


import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.awt.*;

@AnonymousAllowed
@PageTitle("Menu Page")
@Route(value = "Menu")
@RouteAlias(value = "")
public class MenuView extends VerticalLayout {

    /**
     *
     * Attributes
     *
     */
    private H2 Title;
    private Button LoginButton;
    private Button RegisterButton;

    /**
     *
     * Constructor and method call
     */
    public MenuView() {
        AddMenu();
        actionEvents();
    }

    /**
     *
     * Creating the menu
     *
     */
    public void AddMenu() {
        Title = new H2("Main Menu");
        LoginButton = new Button("Login");
        RegisterButton = new Button("Register");
        LoginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        LoginButton.setWidth("400px");
        RegisterButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        RegisterButton.setWidth("400px");
        add(Title, LoginButton, RegisterButton);
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    /**
     *
     * Action events
     *
     */
    public void actionEvents() {
        LoginButton.addClickListener(event -> {
            UI.getCurrent().navigate("Login");
        });
        RegisterButton.addClickListener(event -> {
            UI.getCurrent().navigate("Register");
        });
    }
}
