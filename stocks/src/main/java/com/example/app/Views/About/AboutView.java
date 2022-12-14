package com.example.app.Views.About;

import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
public class AboutView extends VerticalLayout {

    /**
     *
     * Constructor and calling method here
     */
    public AboutView() {
        addAboutSection();
    }


    /**
     *
     * Creating about section
     */
    public void addAboutSection() {
        setSpacing(false);
        add(new H2("About the application"));
        add(new Paragraph("This application was created for my FYP; if you are interested in stocks and financial " +
                "data, this application is for you. It enables you to retrieve a dataset from the stock of your choice, " +
                "analyse it, and uncover interesting trends, by visualising data."));
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");}
}
