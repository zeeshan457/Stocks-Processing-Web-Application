package com.example.app.Data.API;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvIgnore;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *
 * using Lombok annotations to remove boiler code.
 * <p>
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

   @CsvBindByPosition(position = 0)
   private LocalDate Date;
   @CsvBindByPosition(position = 1)
   private Double Open;
   @CsvBindByPosition(position = 2)
   private Double Close;
   @CsvBindByPosition(position = 3)
   private Double High;
   @CsvBindByPosition(position = 4)
   private Double Low;

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
   public StockAPI(LocalDate date, Double open, Double close, Double high, Double low) {
      Date = date;
      Open = open;
      Close = close;
      High = high;
      Low = low;
   }

   /**
    *
    * Creating API method to get data from Yahoo finance API.
    * Data will be fetched based on the symbol.
    *
    * @param grid passing grid, to get current grid
    * @param option passing symbol options, to get current option
    */
   public void getStockFromAPI(Grid<StockAPI> grid, String option, int year) throws IOException {
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
         fromYear.add(Calendar.YEAR, year);
         // get stock
         stock = YahooFinance.get(option, fromYear, toYear, Interval.WEEKLY);
         if (stock != null) {
            Notification message = Notification.show("Successfully, fetched " + option + ", From " + year
                    + " years.");
            message.addThemeVariants(NotificationVariant.LUMO_PRIMARY);

            // Iterating stock history
            for (int i = 0; i < stock.getHistory().size(); i++) {
               // passing i to argument to get all intervals
               String Data = stock.getHistory().get(i).toString();
               // Regex to split the list into specific data and assigning them
               String[] HistoricalData = Data.split("[-(@/,:>]");
               // Split the list based on these symbols, to separate values, then reconstructing the values.
               Date = LocalDate.of(Integer.parseInt(HistoricalData[1]), Integer.parseInt(HistoricalData[2]), Integer.parseInt(HistoricalData[3]));
               Open = Double.valueOf(HistoricalData[6]);
               Close = Double.valueOf(HistoricalData[8]);
               High = Double.valueOf(HistoricalData[5]);
               Low = Double.valueOf(HistoricalData[4]);

               // Getting all the intervals based on the stock selected and positions in the Array.
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
