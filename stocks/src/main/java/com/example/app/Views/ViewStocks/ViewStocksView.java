package com.example.app.Views.ViewStocks;

import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.swing.*;


// Annotations for the class configurations
@PageTitle("View Stocks")
@Route(value = "View-stocks", layout = MainLayout.class)
public class ViewStocksView extends VerticalLayout {
    JTable table;

    public ViewStocksView() {
        setSpacing(false);
        H2 Stocks_Title = new H2("You can view stocks here");
        ComboBox options = new ComboBox<>("Select Stocks");

        // Add components here
        add(options);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);
        getStyle().set("text-align", "center");
    }

}
