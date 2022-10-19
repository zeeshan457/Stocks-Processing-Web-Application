package com.example.app.Views.Home;

import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;


@PageTitle("Home")
@Route(value = "Home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    private TextField name;
    private Button sayHello;

    public HomeView() {
        setSpacing(false);
        Image img = new Image("images/stockgif.gif", "stock image");
        img.setWidth("200px");
        add(img);

        add(new H1("Welcome to the Stocks Application"));
        add(new Paragraph("The stocks application is a platform that enables users to view stocks, " +
                "and process them in a way, which is very user friendly 🤗"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
