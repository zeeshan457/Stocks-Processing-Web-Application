package com.example.app.Data.Filters;

import com.example.app.Data.API.StockAPI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.time.LocalDate;

public class stockFilters {


    /**
     *
     * Attributes
     *
     */
    public ListDataProvider<StockAPI> dataProvider;


    /**
     *
     * Filter greater than, values in column.
     *
     * @param grid passing
     * @param value passing
     * @param option passing
     */
    public void filterGreaterThan(Grid grid, double value, String option) {

        dataProvider = (ListDataProvider<StockAPI>) grid.getDataProvider();

        if (option.equals("Open")) {
            dataProvider.setFilter(stock -> stock.getOpen() >= value);
            Notification message = Notification.show("Filtering " + option + ", More than " + value);
            message.addThemeVariants(NotificationVariant.LUMO_PRIMARY);

        }
        else if (option.equals("Close")) {
            dataProvider.setFilter(stock -> stock.getClose() >= value);
            Notification message = Notification.show("Filtering " + option + ", More than " + value);
            message.addThemeVariants(NotificationVariant.LUMO_PRIMARY);

        }
        else if (option.equals("High")) {
            dataProvider.setFilter(stock -> stock.getHigh() >= value);
            Notification message = Notification.show("Filtering " + option + ", More than " + value);
            message.addThemeVariants(NotificationVariant.LUMO_PRIMARY);

        }
        else if (option.equals("Low")) {
            dataProvider.setFilter(stock -> stock.getLow() >= value);
            Notification message = Notification.show("Filtering " + option + ", More than " + value);
            message.addThemeVariants(NotificationVariant.LUMO_PRIMARY);

        }
    }

    /**
     *
     * Filter less than, values in column.
     *
     * @param grid passing
     * @param value passing
     * @param option passing
     */
    public void filterLessThan(Grid grid, double value, String option) {

        dataProvider = (ListDataProvider<StockAPI>) grid.getDataProvider();

        if (option.equals("Open")) {
            dataProvider.setFilter(stock -> stock.getOpen() <= value);
            Notification message = Notification.show("Filtering " + option + ", Less than " + value);
            message.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        }
        else if (option.equals("Close")) {
            dataProvider.setFilter(stock -> stock.getClose() <= value);
            Notification message = Notification.show("Filtering " + option + ", Less than " + value);
            message.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        }
        else if (option.equals("High")) {
            dataProvider.setFilter(stock -> stock.getHigh() <= value);
            Notification message = Notification.show("Filtering " + option + ", Less than " + value);
            message.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        }
        else if (option.equals("Low")) {
            dataProvider.setFilter(stock -> stock.getLow() <= value);
            Notification message = Notification.show("Filtering " + option + ", Less than " + value);
            message.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        }
    }

    /**
     *
     * Filter by date
     *
     * @param grid passing
     * @param start passing
     * @param end passing
     */
    public void dateFilter(Grid grid, LocalDate start, LocalDate end) {

        dataProvider = (ListDataProvider<StockAPI>) grid.getDataProvider();
            dataProvider.setFilter(stock -> stock.getDate().isAfter(start)
                    && stock.getDate().isBefore(end));

        Notification message = Notification.show("Filtering Date, after " + start + ", before " + end);
        message.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
    }

    /**
     *
     * Reset the filters
     *
     * @param grid passing
     */
    public void resetFilter(Grid grid) {
        dataProvider.setFilter(null);

    }
}
