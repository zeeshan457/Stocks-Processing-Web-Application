package com.example.app.Views.ViewStocks;

import com.example.app.Data.Stock.Stock;
import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.swing.*;


// Annotations for the class configurations
@PageTitle("View Stocks")
@Route(value = "View-stocks", layout = MainLayout.class)
public class ViewStocksView extends VerticalLayout {

    // Attributes
    H2 Stocks_Title = new H2("You can view stocks here");
    ComboBox Options = new ComboBox<>("Select Stocks");
    Button ShowButton = new Button("Show");
    Button ClearButton = new Button("Clear");

    // Constructor
    public ViewStocksView() {
        setSpacing(true);
        // Table to display stock data
        Grid<Stock> grid = new Grid<>(Stock.class, false);
        grid.addColumn(Stock::getCurrency).setHeader("Currency");
        grid.addColumn(Stock::getPrice).setHeader("Price");
        grid.addColumn(Stock::getExchange).setHeader("Exchange");
        grid.addColumn(Stock::getVolume).setHeader("Volume");

        HorizontalLayout ButtonLayout = new HorizontalLayout();
        ButtonLayout.addAndExpand(ClearButton);
        ButtonLayout.addAndExpand(ShowButton);

        // Add components here
        add(Options);
        add(ButtonLayout);
        add(grid);

        // Configs
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);
        getStyle().set("text-align", "center");
    }

}
