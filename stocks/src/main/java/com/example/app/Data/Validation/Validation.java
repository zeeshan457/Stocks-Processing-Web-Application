package com.example.app.Data.Validation;

import com.vaadin.flow.component.notification.Notification;

public class Validation {

    public boolean RegisterValidation(String username, String password1, String password2) {

        if (username.trim().isEmpty() || password1.trim().isEmpty() || password2.trim().isEmpty()) {
            Notification.show("One or more field are empty");
            return false;
        } else if (!password1.equals(password2)) {
            Notification.show("The passwords fields do not match");
            return false;
        }
        return true;
    }

    public boolean LoginValidation(String username, String password1) {

        if (username.trim().isEmpty() || password1.trim().isEmpty()) {
            Notification.show("One or more field are empty");
            return false;
        }

        return true;
    }

    public boolean ComboboxValidation() {


        return true;
    }




}
