package com.example.app.Data.Service;
import com.example.app.Data.Entity.StocksEntity;
import com.example.app.Data.Repository.StocksRepository;
import com.vaadin.flow.component.grid.Grid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


@Service
public class StockService {

    // injecting repository here
    @Autowired
    private StocksRepository repo;
    private StocksEntity stock;

    // Find all stocks in database table
    public Page<StocksEntity> findAllRecords(Pageable pageable) {
        return repo.findAll(pageable);
    }

    // Get all symbols in database table
    public Page<StocksEntity> findAllSymbols(Pageable pageable) {
        return repo.getSymbols(pageable);
    }

    // Save a stock in the database
    public void Save(StocksEntity stock) {
        if (stock == null) {
            stock = new StocksEntity();
        }
        repo.save(stock);
    }

    public Optional<StocksEntity> getSymbols() {
        return repo.findById(1);
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
