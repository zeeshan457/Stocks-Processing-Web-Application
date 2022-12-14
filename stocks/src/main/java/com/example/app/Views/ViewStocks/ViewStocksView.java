package com.example.app.Views.ViewStocks;

import com.example.app.Data.Entity.StocksEntity;
import com.example.app.Data.Service.StockService;
import com.example.app.Data.API.StockAPI;
import com.example.app.Data.Validation.Validation;
import com.example.app.Views.MainLayout;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

@PageTitle("View Stocks")
@Route(value = "View-stocks", layout = MainLayout.class)
public class ViewStocksView extends VerticalLayout {

    /**
     *
     * Attributes, class calls, and injecting
     *
     */
    @Autowired
    private StockService service;
    private Grid<StockAPI> grid;
    private ComboBox<StocksEntity> options = new ComboBox<>("Select Stocks");
    private Button showButton = new Button("Show", VaadinIcon.SEARCH.create());
    private Button refreshButton = new Button("Refresh", VaadinIcon.REFRESH.create());
    private Anchor anchor;
    private StockAPI API = new StockAPI();
    private Validation validate = new Validation();
    private String content = null;

    /**
     *
     * Constructor and method calls
     *
     */
    public ViewStocksView() {
        comboBoxData();
        Export();
        addGrid();
        actionEvents();
    }

    /**
     *
     * Creating the grid to display API data.
     *
     */
    public void addGrid() {
        // Table to display stock data
        grid = new Grid<>(StockAPI.class, false);
        grid.addColumn(StockAPI::getDate).setHeader("Date")
                .setFooter(anchor);
        grid.addColumn(StockAPI::getOpen).setHeader("Open");
        grid.addColumn(StockAPI::getClose).setHeader("Close");
        grid.addColumn(StockAPI::getHigh).setHeader("High");
        grid.addColumn(StockAPI::getLow).setHeader("Low");
        HorizontalLayout ButtonLayout = new HorizontalLayout();
        showButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        ButtonLayout.addAndExpand(refreshButton);
        ButtonLayout.addAndExpand(showButton);
        setSpacing(true);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);
        add(options, ButtonLayout, grid);
    }

    /**
     *
     * Setting data for the combobox, calling method from service.
     */
    public void comboBoxData() {
        options.setAllowCustomValue(false);
        options.setPlaceholder("Select Stock Symbol");
        options.setItems(query -> service.findAllSymbols(
                        (Pageable) PageRequest.of(query.getPage(), query.getPageSize(),
                                VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
    }

    public void Export() {

        StreamResource resource = new StreamResource(
                options.getValue() + "_Stocks_Dataset.csv",
                () -> {
                    try {
                        Stream<StockAPI> stream = grid.getGenericDataView().getItems();
                        StringWriter writer = new StringWriter();
                        StatefulBeanToCsv<StockAPI> bean = new StatefulBeanToCsvBuilder<StockAPI>(writer).build();
                        bean.write(stream);
                        content = writer.toString();

                    } catch (CsvDataTypeMismatchException e) {
                        Notification.show("CsvDataTypeMismatchException");
                    } catch (CsvRequiredFieldEmptyException e) {
                        Notification.show("CsvRequiredFieldEmptyException");
                    }
                        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

                    }
        );
        anchor = new Anchor(resource, "Export");
                }


    /**
     *
     * Action events
     *
     */
    public void actionEvents() {
        showButton.addClickListener(event -> {
            try {
                API.getStockFromAPI(grid, String.valueOf(options.getValue()));
                grid.getDataProvider().refreshAll();
            } catch (IOException e) {
                Notification.show("IO Exception, stock not found");
            }
        });

        refreshButton.addClickListener(event -> {
            API.Refresh();
        });
    }
}
