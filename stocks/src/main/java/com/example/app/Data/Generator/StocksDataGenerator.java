package com.example.app.Data.Generator;


import com.example.app.Application;
import com.example.app.Data.Entity.StocksEntity;
import com.example.app.Data.Repository.StocksRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


public class StocksDataGenerator {
    @Autowired
    StocksRepository repo;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(StocksDataGenerator.class);

    // Bean method to load stock data for processing
    @Bean
    public CommandLineRunner LoadStocks() {
        return args -> {
            logger.info("Generating stock data");
            repo.save(new StocksEntity("GOOG", "Company Google"));
            repo.save(new StocksEntity("TSLA", "Company Tesla"));
            repo.save(new StocksEntity("AMZN", "Company Amazon"));
            repo.save(new StocksEntity("AAPL", "Company Apple"));
            repo.save(new StocksEntity("GOLD", "Company Barrick"));
            // Fetch stock with ID
            StocksEntity customer = repo.findById(1).get();
            // Fetch all stock
            for (StocksEntity stock : repo.findAll()) {
            }
        };
    }
}
