package com.example.app.Views.ManageStocks;

import com.example.app.Data.Entity.StocksEntity;
import com.example.app.Data.Repository.StocksRepository;
import com.example.app.Data.Service.StockService;
import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "Manage", layout = MainLayout.class)
@PageTitle("Manage")
public class ManageStocksView extends VerticalLayout {

    // Attributes and injecting repository here
    @Autowired
    private StocksRepository repo;
    private StockService service;

    private StocksEntity stock;
    private Grid<StocksEntity> grid;
    private Editor editor = new Editor();
    private BeanValidationBinder<StocksEntity> binder;

    // constructor and method calls
    public ManageStocksView() {
        Configure();
        Bindings();
        setSizeFull();
        // add
        add(grid);
        add(editor);
    }

    public void Configure() {
        grid = new Grid<>(StocksEntity.class, true);
        grid.setSizeFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            stock = event.getValue();
            if (stock != null) {
                populateForm(stock);
            } else {
                clearForm();
            }
        });
    }

    public void Bindings() {
        binder = new BeanValidationBinder<>(StocksEntity.class);
        binder.bindInstanceFields(editor);

        // Action events
        editor.getDeleteButton().addClickListener(e -> {
        service.Delete(stock);
        });

        editor.getCancelButton().addClickListener(e -> {
            service.Clear();
            service.Refresh();
        });

        editor.getSaveButton().addClickListener(e -> {
        service.Save(stock);
        });
    }
    void clearForm() {
        service.Populate(stock);
    }

    void populateForm(StocksEntity stock) {
        this.stock = stock;
        binder.readBean(this.stock);
    }
    public void refreshGrid() {
        grid.select(null);
    }
}
