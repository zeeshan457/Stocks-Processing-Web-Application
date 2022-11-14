package com.example.app.Views.ManageStocks;

import com.example.app.Data.Entity.StocksEntity;
import com.example.app.Data.Service.StockService;
import com.example.app.Data.Stock.Stock;
import com.example.app.Data.Validation.Validation;
import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.swing.*;
import java.util.Set;

@Route(value = "Manage", layout = MainLayout.class)
@PageTitle("Manage")
public class ManageStocksView extends VerticalLayout {

    // Attributes and injecting repository here
    @Autowired
    private StockService service;

    private StocksEntity stock;
    private Validation validate = new Validation();
    private Grid grid;
    private TextField symbol;
    private TextField information;
    private TextField symbol2;
    private TextField information2;
    private SplitLayout splitLayout = new SplitLayout();
    private HorizontalLayout layout = new HorizontalLayout();
    BeanValidationBinder<StocksEntity> binder;
    // form1
    private H3 title1;
    private Button cancel;
    private Button delete;
    private Button save;
    private Button refresh;
    // form2
    private H3 title2;
    private Button cancel2;
    private Button delete2;
    private Button add;

    // constructor and method calls
    public ManageStocksView() {
        Grid();
    }

    public void Grid() {
        grid = new Grid<>(StocksEntity.class, false);
        grid.addColumn("symbol").setAutoWidth(true);
        grid.addColumn("information").setAutoWidth(true);
        refresh = new Button("Refresh");
        // Method call to display data in the grid
        displayData(grid);
        // Method calls to display components
        Editor(splitLayout);
        splitLayout.setSizeFull();
        splitLayout.setWidth("700px");
        refresh.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(grid);
        layout.addAndExpand(refresh);
        add(layout);
        add(splitLayout);
        clickListeners();

        // Configs
        grid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);
    }

    public void Editor(SplitLayout layout) {
        // Binders
        binder = new BeanValidationBinder<>(StocksEntity.class);


        HorizontalLayout ButtonLayout = new HorizontalLayout();
        HorizontalLayout ButtonLayout2 = new HorizontalLayout();
        HorizontalLayout Main = new HorizontalLayout();
        FormLayout formEdit = new FormLayout();
        FormLayout formAdd = new FormLayout();
        title1 = new H3("Edit & Delete");
        title2 = new H3("Add New Item");
        symbol = new TextField("Symbol");
        information = new TextField("Information");
        symbol2 = new TextField("Symbol");
        information2 = new TextField("Information");
        save = new Button("Save",VaadinIcon.EDIT.create());
        cancel = new Button("Cancel");
        delete  =new Button("Delete",VaadinIcon.TRASH.create());
        cancel2 = new Button("Cancel");
        add = new Button("New Item", VaadinIcon.PLUS.create());
        delete2  =new Button("Delete");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);
        add.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete2.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);
        formEdit.setWidth("310px");
        formAdd.setWidth("310px");
        ButtonLayout.setWidth("310px");
        ButtonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        ButtonLayout.setSpacing(false);
        ButtonLayout2.setWidth("310px");
       // ButtonLayout2.setJustifyContentMode(JustifyContentMode.BETWEEN);
        ButtonLayout2.setSpacing(true);
        // form layout
        formEdit.add(title1,symbol, information);
        formAdd.add(title2,symbol2,information2);
        // button layout
        ButtonLayout.add(delete, new HorizontalLayout(cancel, save));
        ButtonLayout2.add(cancel2, new HorizontalLayout(add));
        binder.bindInstanceFields(this);
        // add here
        layout.addToPrimary(formEdit, ButtonLayout);
        layout.addToSecondary(formAdd, ButtonLayout2);
    }

    // Click listeners
    public void clickListeners() {
        grid.asSingleSelect().addValueChangeListener(event -> {
            StocksEntity stock = (StocksEntity) event.getValue();
            if (stock != null) {
                bindForm(stock);
            } else {
                clearForm();
            }
        });

        // Action events related to add item form
        cancel2.addClickListener(event -> {
            information2.setValue("");
            symbol2.setValue("");
        });

        add.addClickListener(event -> {
        if (validate.addStockValidation(symbol2.getValue(), information2.getValue())) {
            service.Save(new StocksEntity(symbol2.getValue(), information2.getValue()));
            Notification message = Notification.show("Stock was added");
            message.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            symbol2.setValue("");
            information2.setValue("");
            refreshGrid(grid);

        } else {
            Notification error = Notification.show("Could not add the new item");
            error.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
        });

            // Action events related to add item form
            cancel.addClickListener(event -> {
                information.setValue("");
                symbol.setValue("");
            });
            delete.addClickListener(event -> {
                StocksEntity stocks = new StocksEntity();
                if (validate.deleteStockValidation(symbol.getValue(), information.getValue())) {service.Delete(this.stock);
                   Notification message = Notification.show("Stock was deleted");
                   message.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                   refreshGrid(grid);
                }
            });
            save.addClickListener(event -> {
                if (validate.editStockValidation(symbol.getValue(), information.getValue())) {
                    try {
                        binder.writeBean(this.stock);
                        service.Save(this.stock);
                        Notification message = Notification.show("Stock was updated");
                        message.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        refreshGrid(grid);

                    } catch (ValidationException e) {
                        Notification message = Notification.show("Stock was not updated, ValidationException");
                        message.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    }
                }
            });

        refresh.addClickListener(event -> {
            refreshGrid(grid);
        });
    }

    // Display data to user in grid layout
    public void displayData(Grid grid) {
        // Lazy Loading
        grid.setItems(query -> service.list(
                (Pageable) PageRequest.of(query.getPage(), query.getPageSize(),
                        VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
    }
    // refresh data
    public void refreshGrid(Grid grid) {
        service.Clear(grid);
        displayData(grid);
    }

    public void bindForm(StocksEntity stock) {
        this.stock = stock;
        binder.readBean(this.stock);
    }

    public void clearForm() {
        bindForm(null);
    }
}
