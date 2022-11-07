package com.example.app.Views.ManageStocks;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class Editor extends VerticalLayout {

    // Attributes
    private TextField symbol;
    private TextField information;
    private Button delete;
    private Button cancel;
    private Button save;
    private Button add;

    // Constructor and method calls
    public Editor() {
        SaveDeleteEditor();
//        AddEditor();
    }

    public void SaveDeleteEditor() {
        symbol = new TextField("Symbol");
        information = new TextField("Information");
        delete = new Button("Delete", VaadinIcon.TRASH.create());
        cancel = new Button("Cancel");
        save = new Button("Save");
        // variants
        delete.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        // layouts
        FormLayout form = new FormLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();
        // configs
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        buttonLayout.setSpacing(false);
        // add
        form.add(symbol,information);
        buttonLayout.add(delete, new HorizontalLayout(cancel, save));
        add(form, buttonLayout);
        setWidth("300px");
        setMinWidth("300px");
        setFlexGrow(0);
    }

    public void AddEditor() {
        symbol = new TextField("Symbol");
        information = new TextField("Information");
        cancel = new Button("Cancel");
        add = new Button("Add");
        // variants
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        // layouts
        FormLayout form = new FormLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();
        // configs
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        buttonLayout.setSpacing(false);
        // add
        form.add(symbol,information);
        buttonLayout.add(delete, new HorizontalLayout(cancel, save));
        add(form, buttonLayout);
        setWidth("300px");
    }

    public Button getDeleteButton() {
        return delete;
    }
    public Button getCancelButton() {
        return cancel;
    }
    public Button getSaveButton() {
        return save;
    }
}
