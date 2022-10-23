package com.example.app.Data.Stock;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


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
   private String Name;
   private int currency;
   private int Price;
   private String Exchange;
   private String Volume;
   private String Marketcap;
   private int Change;
   private int Dividend ;
   
}
