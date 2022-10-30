package com.example.app.Views.Home;

import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;


@PageTitle("Home")
@Route(value = "Home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    // Constructor and calling method here
    public HomeView() {
        setSpacing(false);
        addHome();
        // Configs
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    public void addHome() {
        H1 Title = new H1("Welcome to the Stocks Application");
        Image HomeImage = new Image("images/stockgif.gif", "stock image");
            Paragraph Information = new Paragraph("The stocks application is a platform that enables users to view stocks, " +
                    "and process them in a way, which is very user friendly 🤗");
            HomeImage.setWidth("200px");
            // Adding components
            add(HomeImage);
            add(Title);
            add(Information);

    }
}
