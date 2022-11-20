package com.example.app.Data.API;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


/**
 *
 * using Lombok annotations to remove boiler code.
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockAPI {

   // Attributes
   private yahoofinance.Stock stock;
   private Calendar fromYear;
   private Calendar toYear;
   private String Date;
   private String Open;
   private String Close;
   private String High;
   private String Low;
   private static int increment = 0;
   List<StockAPI> dataProvider = new ArrayList<>();

   // Construct the stockAPI object
   public StockAPI(String date, String open, String close, String high, String low) {
      Date = date;
      Open = open;
      Close = close;
      High = high;
      Low = low;
   }

   public void getStockFromAPI(Grid<StockAPI> grid, String option) throws IOException {
      // start year
      fromYear = Calendar.getInstance();
      // Current year
      toYear = Calendar.getInstance();
      fromYear.add(Calendar.YEAR, -5);
      // get stock
      stock = YahooFinance.get(option, fromYear, toYear, Interval.WEEKLY);

      if (stock != null) {
         // Iterating stock history
         for (int i = 0; i < stock.getHistory().size(); i++) {
            // passing i to argument to get all intervals
            String Data = stock.getHistory().get(i).toString();

            // Regex to split the list into specific data and assigning them
            String[] HistoricalData = Data.split("[-(@/,:>]");

            // Split the list based on these symbols, to separate values, then reconstructing the values.
            Date = HistoricalData[1] + "-" + HistoricalData[2] + "-" + HistoricalData[3];
            Open = HistoricalData[6];
            Close = HistoricalData[8];
            High = HistoricalData[5];
            Low = HistoricalData[4];

            // Getting all the intervals based on the stock selected and positions in the Arr.
            dataProvider.add(new StockAPI(Date, Open, Close, High, Low));
            grid.getDataProvider().refreshAll();
         }
      }

      grid.setItems(dataProvider);
   }

   public void Refresh() {
      UI.getCurrent().getPage().reload();
   }
}
