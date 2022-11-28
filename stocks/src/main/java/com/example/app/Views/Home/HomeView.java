package com.example.app.Views.Home;

import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;


@PageTitle("Home")
@Route(value = "Home", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    /**
     *
     * Constructor and calling method here
     */
    public HomeView() {
        checkLogin();
        addHome();
    }

    /**
     *
     * Creating the Home section
     *
     */
    public void addHome() {
        setSpacing(false);
        H1 Title = new H1("Welcome to the Stocks Application");
        Image HomeImage = new Image("images/stockgif.gif", "stock image");
            Paragraph Information = new Paragraph("The stocks application is a platform that enables users to view stocks, " +
                    "and process them in a way, which is very user friendly.");
        Paragraph paragraph = new Paragraph("The view stock page will enable users to find historical data for any stock " +
                "selected in the list. Also, the data can be converted into a dataset. The processing page will enable users to " +
                "process historical data from a dataset. Admins on the system can manage stocks by using CRUD functionality.");
            HomeImage.setWidth("200px");
            add(HomeImage, Title, Information, paragraph);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    /**
     *
     * Checking login of user.
     *
     */
    public void checkLogin() {
        VaadinSession session = VaadinSession.getCurrent();
        try {
            session.getAttribute("username").toString();
        } catch(Exception e) {
            Notification.show("You are not logged in");
        }
    }
}
