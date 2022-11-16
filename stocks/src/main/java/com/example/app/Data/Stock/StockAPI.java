package com.example.app.Data.Stock;


import com.vaadin.flow.component.grid.Grid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;


/**
 *
 * using Lombok annotations to remove boiler code.
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class StockAPI {

   // Attributes
   private yahoofinance.Stock stock;
   private String Date;
   private Calendar fromYear;
   private Calendar toYear;
   private BigDecimal Open;
   private BigDecimal Close;
   private BigDecimal High;
   private BigDecimal Low;

   public StockAPI() {

   }

   public void getStockFromAPI(Grid grid, String option) throws IOException {
      // start year
      fromYear = Calendar.getInstance();
      // Current year
      toYear = Calendar.getInstance();
      fromYear.add(Calendar.YEAR, -5);

      // get stock
      stock = YahooFinance.get(option, fromYear, toYear, Interval.WEEKLY);

      Date = fromYear.toString();
      //Open = stock.getQuote().getOpen();
      Close = stock.getQuote().getPreviousClose();
      High = stock.getQuote().getDayHigh();
      Low = stock.getQuote().getDayLow();

      grid.setItems(stock.getQuote());

   }

   public String getDate() {
      return Date;
   }
}
