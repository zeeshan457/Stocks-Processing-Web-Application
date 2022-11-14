package com.example.app.Data.Service;


import com.example.app.Data.Entity.StocksEntity;
import com.example.app.Data.Entity.UserEntity;
import com.example.app.Data.Repository.StocksRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Collection;
import org.springframework.data.domain.PageRequest;
import java.util.List;

@Service
public class StockService {

    // injecting repository here
    @Autowired
    private StocksRepository repo;
    private StocksEntity stock;

    // Find all stocks in database
    public Page<StocksEntity> list(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // Save a stock in the database
    public void Save(StocksEntity stock) {
        if (stock == null) {
            stock = new StocksEntity();
        }
        repo.save(stock);
    }

    // Delete a stock in the database
    public void Delete(StocksEntity stock) {
        if (stock != null) {
            repo.delete(stock);
        }
    }

    public void Clear(Grid grid) {
    grid.select(null);
    }
}
