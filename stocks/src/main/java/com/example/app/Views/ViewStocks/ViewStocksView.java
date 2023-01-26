package com.example.app.Views.ViewStocks;

import com.example.app.Data.Entity.StocksEntity;
import com.example.app.Data.Filters.stockFilters;
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
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private ComboBox<String> optionsColoumn = new ComboBox<>();
    private TextField filterField = new TextField();
    private IntegerField yearField = new IntegerField("Select Year");
    private DatePicker startDate = new DatePicker();
    private DatePicker endDate = new DatePicker();
    private Button showButton = new Button("Show", VaadinIcon.SEARCH.create());
    private Button resetFilterButton = new Button("Reset Filter", VaadinIcon.FILTER.create());
    private Button refreshButton = new Button("Refresh", VaadinIcon.REFRESH.create());
    private Button greaterThanButton;
    private Button lessThanButton;
    private Button dateButton;
    private Anchor anchor;
    private StockAPI API = new StockAPI();
    private Validation validate = new Validation();
    private stockFilters filter = new stockFilters();
    private String content = null;
    LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());


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

        List<String> dataProvider = new ArrayList<String>();
        dataProvider.add("Open");
        dataProvider.add("Close");
        dataProvider.add("High");
        dataProvider.add("Low");
        optionsColoumn.setPlaceholder("Select Column");
        optionsColoumn.setItems(dataProvider);

        filterField = new TextField();
        filterField.setPlaceholder("Value");
        greaterThanButton = new Button("Filter Greater Than");
        lessThanButton = new Button("Filter Less Than");
        dateButton = new Button("Filter Date");
        HorizontalLayout ButtonLayout = new HorizontalLayout();
        HorizontalLayout selectLayout = new HorizontalLayout();
        HorizontalLayout valueFilterLayout = new HorizontalLayout();
        HorizontalLayout dateFilterLayout = new HorizontalLayout();
        showButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        resetFilterButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        refreshButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        yearField.setMin(-5);
        yearField.setMax(-1);
        yearField.setValue(-5);
        yearField.setHasControls(true);
        startDate.setPlaceholder("Start Date dd-MM-yyyy");
        endDate.setPlaceholder("End Date dd-MM-yyyy");

        // set dates
        startDate.setMax(currentDate);
        endDate.setMax(currentDate);

        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();

        selectLayout.add(options, yearField);
        ButtonLayout.addAndExpand(refreshButton, resetFilterButton, showButton);
        valueFilterLayout.addAndExpand(optionsColoumn, filterField, lessThanButton, greaterThanButton);
        dateFilterLayout.addAndExpand(startDate, endDate, dateButton);

        setSpacing(true);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);
        add(selectLayout, ButtonLayout, valueFilterLayout, dateFilterLayout, grid);
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





    /**
     *
     * Exports the grid CSV
     *
     */
    public void Export() {

            StreamResource resource = new StreamResource(
                    options.getValue() + "_Stocks_Dataset.csv",
                    () -> {
                        try {
                            // Objects to get grid data, write data, and assign to string.
                            Stream<StockAPI> stream = grid.getGenericDataView().getItems();
                            StringWriter writer = new StringWriter();
                            StatefulBeanToCsv<StockAPI> bean = new StatefulBeanToCsvBuilder<StockAPI>(writer)
                                    .build();
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
            anchor.setEnabled(true);
    }

    /**
     *
     * Action events
     *
     */
    public void actionEvents() {
        showButton.addClickListener(event -> {
            try {
                API.getStockFromAPI(grid, String.valueOf(options.getValue()), yearField.getValue());

            } catch (IOException e) {
                Notification.show("IO Exception, stock not found");
            }
        });
        refreshButton.addClickListener(event -> {
            API.Refresh();
        });

        resetFilterButton.addClickListener(event -> {
            filter.resetFilter(grid);
        });

        dateButton.addClickListener(event -> {
            if (validate.selectDate(startDate.getValue(), endDate.getValue())) {
                filter.dateFilter(grid, startDate.getValue(), endDate.getValue());
            }
        });

        greaterThanButton.addClickListener(event -> {
            if (validate.selectValue(String.valueOf(optionsColoumn.getValue()), filterField.getValue())) {
                filter.filterGreaterThan(grid, Double.parseDouble(filterField.getValue()), String.valueOf(optionsColoumn.getValue()));
            }
        });
        lessThanButton.addClickListener(event -> {
           if (validate.selectValue(String.valueOf(optionsColoumn.getValue()), filterField.getValue())) {
               filter.filterLessThan(grid, Double.parseDouble(filterField.getValue()), String.valueOf(optionsColoumn.getValue()));
            }
        });
    }
}
