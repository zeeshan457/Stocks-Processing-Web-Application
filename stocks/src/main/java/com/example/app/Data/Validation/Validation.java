package com.example.app.Data.Validation;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

import java.time.LocalDate;

public class Validation {

    /**
     *
     * Validates a user based on the registration fields
     *
     * @param username passing username, to get current username.
     * @param password1 passing password, to get current password.
     * @param password2 passing secondary password, to get current secondary password.
     * @return True or False, based on condition.
     */
    public boolean RegisterValidation(String username, String password1, String password2) {
        if (username.trim().isEmpty() || password1.trim().isEmpty() || password2.trim().isEmpty()) {
            Notification error = Notification.show("One or more field are empty");
            error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        } else if (!password1.equals(password2)) {
            Notification error = Notification.show("The passwords fields do not match");
            error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        } else if (username.length() < 5 || password1.length() < 5) {
            Notification error = Notification.show("The username or password must be more than 5 characters");
            error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        }
        return true;
    }

    /**
     *
     * Validates a user based on the login fields
     *
     * @param username passing username, to get current username.
     * @param password1 passing password, to get current password.
     * @return True or False, based on condition.
     */
    public boolean LoginValidation(String username, String password1) {
        if (username.trim().isEmpty() || password1.trim().isEmpty()) {
            Notification error = Notification.show("One or more field are empty");
            error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        }
        return true;
    }

    /**
     *
     * Validates add stock
     *
     * @param symbol passing symbol, to get current symbol.
     * @param information passing information, to get current information.
     * @return True or False, based on condition.
     */
    public boolean addStockValidation(String symbol, String information) {
        if (symbol.trim().isEmpty() || information.trim().isEmpty()) {
            Notification error = Notification.show("One or more field are empty");
            error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        }
        return true;
    }

    /**
     *
     * Validates edit stocks
     *
     * @param symbol passing symbol, to get current symbol.
     * @param information passing information, to get current information.
     * @return True or False, based on condition.
     */
    public boolean editStockValidation(String symbol, String information) {
        if (symbol.trim().isEmpty() || information.trim().isEmpty()) {
            Notification error = Notification.show("One or more field are empty");
            error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        }
        return true;
    }

    /**
     *
     * Validates delete stock
     *
     * @param symbol passing symbol, to get current symbol.
     * @param information passing information, to get current information.
     * @return True or False, based on condition.
     */
    public boolean deleteStockValidation(String symbol, String information) {
        if (symbol.trim().isEmpty() || information.trim().isEmpty()) {
            Notification error = Notification.show("One or more field are empty");
            error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        }
        return true;
    }

    /**
     *
     * Validates stock selection
     *
     * @param symbol passing symbol, to get current symbol.
     * @return True or False, based on condition.
     */
    public boolean selectStockValidation(String symbol) {
        if (symbol.trim().isEmpty()) {
            Notification error = Notification.show("Please select a stock from the list");
            error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        }
    return true;
    }

    /**
     *
     *
     *
     * @param column the selected column
     * @param search value typed
     * @return true or false
     */
    public boolean selectValue(String column, String search) {

        if (column.trim().equals("null")) {
            Notification error = Notification.show("Please select a column");
            error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        }
        if (search.isEmpty()) {
            Notification error = Notification.show("Please type a value");
            error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        }
        else if (!search.isEmpty()) {
            try {
                Double value  = Double.parseDouble(search);
            } catch (NumberFormatException nfe) {
                Notification error = Notification.show("NumberFormat Exception");
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return false;
            }
        }
        return true;
    }

    /**
     *
     * Validates a date form to filter data.
     *
     * @param start from date
     * @param end end date
     * @return true or false
     */
    public boolean selectDate(LocalDate start, LocalDate end) {
        if (start == null) {
            Notification error = Notification.show("Start date is Null");
            error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;

        } else if (end == null) {
            Notification error = Notification.show("End date is Null");
            error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        }
        return true;
    }
    }
