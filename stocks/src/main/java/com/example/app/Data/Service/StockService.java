package com.example.app.Data.Service;


import com.example.app.Data.Entity.StocksEntity;
import com.example.app.Data.Repository.StocksRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    // injecting repository here
    @Autowired
    private StocksRepository repo;
    private BeanValidationBinder<StocksEntity> binder;
    private Grid grid;
    private StocksEntity stock;

    public void Save(StocksEntity stock) {
        try {
            if (stock == null) {
                stock = new StocksEntity();
            }
            binder.writeBean(stock);
            repo.save(stock);
            Clear();
            Refresh();
            Notification.show("Stock details updated.");

        } catch (ValidationException validationException) {
            Notification.show("Please enter valid stock details.");
        }

    }
    public void Delete(StocksEntity stock) {
        if (stock != null) {
            repo.delete(stock);
            Clear();
            Refresh();
            Notification.show("Stock was deleted.");
        }
    }

    public void selectRow(StocksEntity stock) {
        if (stock != null) {
            Populate(stock);
        } else {
            Clear();
        }

    }
    public void Refresh() {
        grid.select(null);
    }
    public void Clear() {
        Populate(null);
    }

    public void Populate(StocksEntity stock) {
        binder = new BeanValidationBinder<>(StocksEntity.class);
        Binder<Object> binder;
        binder.readBean(stock);
    }







}
