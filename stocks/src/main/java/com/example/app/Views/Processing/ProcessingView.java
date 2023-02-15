package com.example.app.Views.Processing;

import com.example.app.Data.API.StockAPI;
import com.example.app.Data.Entity.StocksEntity;
import com.example.app.Data.Machine_Learning.LSTM_Algorithm;
import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.io.*;


@PageTitle("Processing")
@Route(value = "Processing", layout = MainLayout.class)
public class ProcessingView extends VerticalLayout {

    /**
     *
     * Attributes
     *
     */
    private MemoryBuffer memoryBuffer = new MemoryBuffer();
    private Upload upload = new Upload(memoryBuffer);
    private Button processButton;
    private Button uploadButton;
    private File file;
    private LSTM_Algorithm model = new LSTM_Algorithm();
    private StockAPI API = new StockAPI();
    private Chart lineChart = new Chart(ChartType.LINE);
    private Chart scatterChart = new Chart(ChartType.SCATTER);
    private Chart barChart = new Chart(ChartType.BAR);
    private Configuration configurationlineChart = lineChart.getConfiguration();
    private Configuration configurationscatterChart = scatterChart.getConfiguration();
    private Configuration configurationbarChart = barChart.getConfiguration();
    private NumberField ratefield = new NumberField("Learning Rate");
    private IntegerField epochsfield = new IntegerField("Epochs");
    private Grid grid;

    /**
     *
     * Constructor and method calls
     *
     */
    public ProcessingView() {
        addFileOpener();
        Charts();
        actionEvents();
    }

    /**
     *
     * Creating the file section
     *
     */
    public void addFileOpener() {
        setSpacing(true);
        HorizontalLayout buttonLayout = new HorizontalLayout();
        processButton = new Button("Process", VaadinIcon.FILE_PROCESS.create());
        uploadButton = new Button("Upload CSV", VaadinIcon.UPLOAD.create());
        processButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        uploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        upload.setUploadButton(uploadButton);
        upload.setAcceptedFileTypes("application/csv", ".csv");
        upload.setMaxFileSize(1024 * 1024 * 5); //5MB
        upload.setWidthFull();
        buttonLayout.addAndExpand(processButton);
        add(upload, buttonLayout);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);
    }

    public void Charts() {
        setSpacing(true);
        HorizontalLayout Chartlayout = new HorizontalLayout();
        HorizontalLayout inputlayout = new HorizontalLayout();

        ratefield.setMin(0.001);
        ratefield.setMax(0.1);
        ratefield.setValue(0.001);
        ratefield.setHasControls(true);

        epochsfield.setMin(10);
        epochsfield.setMax(100);
        epochsfield.setValue(100);
        epochsfield.setHasControls(true);

//        XAxis xAxis = new XAxis();
//        xAxis.setType(AxisType.DATETIME);
//        configurationlineChart.addxAxis(xAxis);
//        configurationscatterChart.addxAxis(xAxis);
//        configurationbarChart.addxAxis(xAxis);

        configurationlineChart.setTitle("Line-chart Predictions");
        configurationlineChart.setExporting(true);
        configurationlineChart.getxAxis().setTitle("Time step");
        configurationlineChart.getyAxis().setTitle("Predicted value");

        configurationscatterChart.setTitle("Scatter-chart Predictions");
        configurationscatterChart.setExporting(true);
        configurationscatterChart.getxAxis().setTitle("Time step");
        configurationscatterChart.getyAxis().setTitle("Predicted value");

        configurationbarChart.setTitle("Bar-chart Predictions");
        configurationbarChart.setExporting(true);
        configurationbarChart.getxAxis().setTitle("Time step");
        configurationbarChart.getyAxis().setTitle("Predicted value");

        inputlayout.add(ratefield, epochsfield);
        Chartlayout.addAndExpand(lineChart, scatterChart, barChart);
        add(inputlayout, Chartlayout);
    }

    /**
     *
     * Action events.
     * Server side handling and listeners.
     *
     */
    public void actionEvents() {
        upload.addSucceededListener(event -> {
            // get file
            file = new File(event.getFileName());
            Notification notification = Notification.show(file.getName()
                    + "\n " + file.getAbsolutePath());
            notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        });

        upload.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();
            Notification notification = Notification.show(errorMessage + " Files must be .CSV");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });

        processButton.addClickListener(event -> {
            if (file != null) {
                Notification notification = Notification.show("Processing the Dataset " + file.getName());
                notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);

                try {
                    model.lstmModel(file.getName(), memoryBuffer, ratefield.getValue(), epochsfield.getValue(),
                                        lineChart, scatterChart, barChart, grid);
                    file = null;

                } catch (IOException e) {
                    Notification errorFile = Notification.show("IOException Error");
                    errorFile.addThemeVariants(NotificationVariant.LUMO_ERROR);
                } catch (InterruptedException e) {
                    Notification errorFile = Notification.show("InterruptedException Error");
                    errorFile.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }

            } else {
                Notification errorFile = Notification.show("Please upload a file");
                    errorFile.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
    }
}
