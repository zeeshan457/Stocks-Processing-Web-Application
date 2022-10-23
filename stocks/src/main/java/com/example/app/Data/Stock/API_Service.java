package com.example.app.Data.Stock;

import com.vaadin.flow.component.notification.Notification;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import java.io.IOException;

/**
 *
 */
@Service
public class API_Service {

    // Attributes
    private Stock stock;

    public void getStock(String options) throws IOException {
    try {
        stock = YahooFinance.get(options);


    } catch (IOException e) {
        Notification.show("IOException Error. The stock doesn't exist");
        }
    }
}
