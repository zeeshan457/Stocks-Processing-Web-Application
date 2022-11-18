package com.example.app.Views.ViewStocks;

import com.example.app.Data.Entity.StocksEntity;
import com.example.app.Data.Service.StockService;
import com.example.app.Data.API.StockAPI;
import com.example.app.Data.Validation.Validation;
import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

// Annotations for the class configurations
@PageTitle("View Stocks")
@Route(value = "View-stocks", layout = MainLayout.class)
public class ViewStocksView extends VerticalLayout {

    // Attributes
    Grid<StockAPI> grid;
    ComboBox<StocksEntity> options = new ComboBox<>("Select Stocks");
    Button showButton = new Button("Show", VaadinIcon.STOCK.create());
    Button clearButton = new Button("Clear");
    Button writeButton = new Button("Write to CSV", VaadinIcon.FILE.create());

    @Autowired
    public StockService service;

    StockAPI APIservice = new StockAPI();
    Validation validate = new Validation();

    // Constructor and method calls
    public ViewStocksView() {
        comboBoxData();
        addGrid();
        actionEvents();
    }
    public void addGrid() {
        // Table to display stock data
        grid = new Grid<>(StockAPI.class, false);

        grid.addColumn(StockAPI::getDate).setHeader("Date");
        grid.addColumn(StockAPI::getOpen).setHeader("Open");
        grid.addColumn(StockAPI::getClose).setHeader("Close");
        grid.addColumn(StockAPI::getHigh).setHeader("High");
        grid.addColumn(StockAPI::getLow).setHeader("Low");

        HorizontalLayout ButtonLayout = new HorizontalLayout();
        showButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clearButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        writeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        ButtonLayout.addAndExpand(clearButton);
        ButtonLayout.addAndExpand(writeButton);
        ButtonLayout.addAndExpand(showButton);
        setSpacing(true);
        // Configs
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);
        add(options, ButtonLayout, grid);
    }

    public void comboBoxData() {
        options.setAllowCustomValue(false);
        options.setPlaceholder("Select Stock Symbol");
        options.setItems(query -> service.findAllSymbols(
                        (Pageable) PageRequest.of(query.getPage(), query.getPageSize(),
                                VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
    }

    public void actionEvents() {
        showButton.addClickListener(event -> {
            try {
                    Notification message = new Notification("Please select a value");
                    APIservice.getStockFromAPI(grid, String.valueOf(options.getValue()));

            } catch (IOException e) {
                Notification.show("IO Exception");
            }
        });

        clearButton.addClickListener(event -> {
            grid.select(null);

        });
    }
}
