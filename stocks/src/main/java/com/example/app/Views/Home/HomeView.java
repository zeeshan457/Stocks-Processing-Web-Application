package com.example.app.Views.Home;

import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;


@PageTitle("Home")
@Route(value = "Home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    // Attributes
    private H1 Ttile = new H1("Welcome to the Stocks Application");
    private Image HomeImage = new Image("images/stockgif.gif", "stock image");
    private Paragraph Information = new Paragraph("The stocks application is a platform that enables users to view stocks, " +
                                                  "and process them in a way, which is very user friendly 🤗");

    // Constructor
    public HomeView() {
        setSpacing(false);
        HomeImage.setWidth("200px");
        // Adding components
        add(HomeImage);
        add(Ttile);
        add(Information);

        // Configs
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
