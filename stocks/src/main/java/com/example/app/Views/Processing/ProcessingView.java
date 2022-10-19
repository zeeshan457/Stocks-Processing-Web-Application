package com.example.app.Views.Processing;

import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

// Annotations for this class
@PageTitle("Processing")
@Route(value = "Processing", layout = MainLayout.class)
public class ProcessingView extends VerticalLayout {

    public ProcessingView() {
        setSpacing(false);
        H2 Stocks_Title = new H2("Process your stocks here");
        ComboBox stock_options = new ComboBox<>("Select Stocks");
        ComboBox Algo_options = new ComboBox<>("Select Algorithm");

        // Add components here
        add(stock_options);
        add(Algo_options);


        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);
        getStyle().set("text-align", "center");
    }

}
