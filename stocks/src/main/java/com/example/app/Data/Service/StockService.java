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

    /**
     *
     * Attributes and injecting
     *
     */
    @Autowired
    private StocksRepository repo;
    private StocksEntity stock;

    /**
     *
     * Find all stocks in database table
     *
     * @param pageable passing page, to get current page
     * @return pageable object, containing all stock entities.
     */
    public Page<StocksEntity> findAllRecords(Pageable pageable) {
        return repo.findAll(pageable);
    }


    /**
     *
     * Get all symbols in database table
     *
     * @param pageable passing page, to get current page
     * @return pageable object, containing all symbols.
     */
    public Page<StocksEntity> findAllSymbols(Pageable pageable) {
        return repo.getSymbols(pageable);
    }

    /**
     *
     * Save a stock in the database
     *
     * @param stock passing stock, to get current stock
     */
    public void Save(StocksEntity stock) {
        if (stock == null) {
            stock = new StocksEntity();
        }
        repo.save(stock);
    }

    /**
     *
     * get symbol based on ID
     *
     * @return optional object of an index, containing symbol
     */
    public Optional<StocksEntity> getSymbols() {
        return repo.findById(1);
    }

    /**
     *
     * Delete a stock in the database
     *
     * @param stock passing stock, to get current stock.
     */
    public void Delete(StocksEntity stock) {
        if (stock != null) {
            repo.delete(stock);
        }
    }

    /**
     *
     * Clears the grid
     *
     * @param grid passing grid, to get current grid.
     */
    public void Clear(Grid grid) {
    grid.select(null);
    }
}
