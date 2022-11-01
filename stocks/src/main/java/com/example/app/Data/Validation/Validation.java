package com.example.app.Data.Validation;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.shared.ThemeVariant;

public class Validation {

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

    public boolean LoginValidation(String username, String password1) {
        if (username.trim().isEmpty() || password1.trim().isEmpty()) {
            Notification error = Notification.show("One or more field are empty");
            error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        } else if (username.length() < 5 || password1.length() < 5) {
            Notification error = Notification.show("The username or password must be more than 5 characters");
            error.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;

        }
        return true;
    }

    public boolean ComboboxValidation() {
        return true;
    }
}
