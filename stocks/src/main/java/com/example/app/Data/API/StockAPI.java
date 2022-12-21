package com.example.app.Data.API;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;
import java.io.IOException;
import java.util.*;

/**
 *
 * using Lombok annotations to remove boiler code.
 *
 * Annotations for CSV exports
 *
 */
@Getter
@Setter
@NoArgsConstructor

public class StockAPI {

   /**
    *
    * Attributes and injecting
    * <p>
    * Annotations from OPENCSV library to order the columns
    */


   private String Date;
   private String Open;
   private String Close;
   private String High;
   private String Low;

   /**
    *
    * Construct the stockAPI object
    *
    * @param date type String
    * @param open type String
    * @param close type String
    * @param high type String
    * @param low type String
    */
   public StockAPI(String date, String open, String close, String high, String low) {
      Date = date;
      Open = open;
      Close = close;
      High = high;
      Low = low;
   }

   /**
    *
    * Creating API method to get  data from Yahoo finance API.
    *
    * @param grid passing grid, to get current grid
    * @param option passing symbol options, to get current option
    * @throws IOException
    */
   public void getStockFromAPI(Grid<StockAPI> grid, String option) throws IOException {
      if (option.equals("null")) {
        Notification error = Notification.show("Please select a stock from the list");
         error.addThemeVariants(NotificationVariant.LUMO_ERROR);
      } else {
         yahoofinance.Stock stock;
         Calendar fromYear;
         Calendar toYear;
         List<StockAPI> dataProvider = new ArrayList<>();
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
               Date = HistoricalData[3] + "/" + HistoricalData[2] + "/" + HistoricalData[1];
               Open = HistoricalData[6];
               Close = HistoricalData[8];
               High = HistoricalData[5];
               Low = HistoricalData[4];
               // Getting all the intervals based on the stock selected and positions in the Arr.
               dataProvider.add(new StockAPI(Date, Open, Close, High, Low));
            }
         }
         grid.setItems(dataProvider);
      }
   }

   /**
    *
    * Refresh the current page, to refresh the grid.
    *
    */
   public void Refresh() {
      UI.getCurrent().getPage().reload();
   }
}
