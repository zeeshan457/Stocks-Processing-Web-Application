package com.example.app.Data.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvBindByPosition;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
   public void getStockFromAPI(Grid<StockAPI> grid, String option, int year) throws IOException, JSONException {

      if (option.equals("null")) {
         Notification error = Notification.show("Please select a stock from the list");
         error.addThemeVariants(NotificationVariant.LUMO_ERROR);
      } else {
         String symbol = option.toUpperCase();
         List<StockAPI> dataProvider = new ArrayList<>();

         // start year and valid date
         LocalDate startDate = LocalDate.now().minusYears(Math.abs(year));
         if (startDate.isAfter(LocalDate.now())) {
            startDate = LocalDate.now().minusYears(1);
         }

         String url = String.format("https://query1.finance.yahoo.com/v8/finance/chart/%s?interval=1d&period1=%d&period2=%d",
                 symbol, startDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC), LocalDate.now().atStartOfDay().toEpochSecond(ZoneOffset.UTC));
         // make the HTTP request
         URLConnection connection = new URL(url).openConnection();
         connection.connect();
         BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

         StringBuilder responseBuilder = new StringBuilder();
         String inputLine;
         while ((inputLine = in.readLine()) != null) {
            responseBuilder.append(inputLine);
         }
         String responseString = responseBuilder.toString();
         Map<String, Object> responseMap = new ObjectMapper().readValue(responseString, Map.class);

         // parse the JSON response
         JSONObject jsonObj = new JSONObject(responseMap);
         JSONArray resultArr = jsonObj.getJSONObject("chart").getJSONArray("result");
         JSONObject resultObj = resultArr.getJSONObject(0);
         JSONArray timestampArr = resultObj.getJSONArray("timestamp");
         JSONArray openArr = resultObj.getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("open");
         JSONArray closeArr = resultObj.getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("close");
         JSONArray highArr = resultObj.getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("high");
         JSONArray lowArr = resultObj.getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("low");

         // convert the JSON data to StockAPI objects
         for (int i = 0; i < timestampArr.length(); i++) {
            LocalDate date = Instant.ofEpochSecond(timestampArr.getLong(i)).atZone(ZoneOffset.UTC).toLocalDate();
            Double open = openArr.getDouble(i);
            Double close = closeArr.getDouble(i);
            Double high = highArr.getDouble(i);
            Double low = lowArr.getDouble(i);
            dataProvider.add(new StockAPI(date, open, close, high, low));
         }

         grid.setItems(dataProvider);

         Notification message = Notification.show(String.format("Successfully, fetched %s, From %d years.", option, year));
         message.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
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
