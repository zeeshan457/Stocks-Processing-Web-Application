package com.example.app.Data.Stock;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
public class Stock {

   // Attributes
   private yahoofinance.Stock stock;
   private String Date;
   private Calendar fromYear;
   private Calendar toYear;
   private BigDecimal Open;
   private BigDecimal Close;
   private BigDecimal High;
   private BigDecimal Low;

   public void getStock(String option) throws IOException {

      // start year
      fromYear = Calendar.getInstance();
      // Current year
      toYear = Calendar.getInstance();

      fromYear.add(Calendar.YEAR, -5);
      stock = YahooFinance.get(option, fromYear, toYear, Interval.WEEKLY);

      Date = fromYear.toString();
      Open = stock.getQuote().getOpen();
      Close = stock.getQuote().getPreviousClose();
      High = stock.getQuote().getDayHigh();
      Low = stock.getQuote().getDayLow();

   }

   public String getDate() {
      return Date;
   }
}
