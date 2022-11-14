package com.example.app.Data.Generator;

import com.example.app.Data.Entity.StocksEntity;
import com.example.app.Data.Repository.StocksRepository;
import com.helger.commons.log.InMemoryLogger;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;



public class StocksDataProvider {

//    private static Logger log = (Logger) LoggerFactory
//            .getLogger(StocksDataProvider.class);

//    @Bean
//    public CommandLineRunner loadData(@Autowired StocksRepository repo) {
//        return (args) -> {
//            repo.save(new StocksEntity("GOOG", "Information"));
//            repo.save(new StocksEntity("AMZN", "Information"));
//            repo.save(new StocksEntity("APPL", "Information"));
//            repo.save(new StocksEntity("TSLA", "Information"));
//            repo.save(new StocksEntity("GOLD", "Information"));
//
//            // fetch all stocks
//            log.info("Customers found with findAll():");
//            log.info("-------------------------------");
//            for (StocksEntity customer : repo.findAll()) {
//                log.info(customer.toString());
//            }
//            log.info("");
//        };
//    }
}
