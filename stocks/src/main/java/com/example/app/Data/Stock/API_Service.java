package com.example.app.Data.Stock;

import com.vaadin.flow.component.notification.Notification;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import java.io.IOException;

/**
 *
 */
@Service
@AllArgsConstructor
@NoArgsConstructor
public class API_Service {

    // Attributes, Stock object from Yahoo finance API
    private Stock stock;

    public void getStock(String options) throws IOException {
    try {
        stock = YahooFinance.get(options);


    } catch (IOException e) {
        Notification.show("IOException Error. The stock doesn't exist");
        }
    }
}
