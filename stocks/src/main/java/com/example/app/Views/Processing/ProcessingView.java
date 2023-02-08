package com.example.app.Views.Processing;

import com.example.app.Data.Machine_Learning.LSTM_Algorithm;
import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
    private static File file;
    private LSTM_Algorithm model = new LSTM_Algorithm();
    private Chart lineChart = new Chart(ChartType.LINE);
    private Chart scatterChart = new Chart(ChartType.SCATTER);
    private Chart barChart = new Chart(ChartType.BAR);
    private Configuration configurationlineChart = lineChart.getConfiguration();
    private Configuration configurationscatterChart = scatterChart.getConfiguration();
    private Configuration configurationbarChart = barChart.getConfiguration();

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
        HorizontalLayout chartLayout1 = new HorizontalLayout();

        XAxis xAxis = new XAxis();
        xAxis.setType(AxisType.DATETIME);
        configurationlineChart.addxAxis(xAxis);
        configurationscatterChart.addxAxis(xAxis);
        configurationbarChart.addxAxis(xAxis);

        configurationlineChart.setTitle("Line chart Predictions");
        configurationlineChart.setExporting(true);
        configurationlineChart.getxAxis().setTitle("Date");
        configurationlineChart.getyAxis().setTitle("Stock Price");

        configurationscatterChart.setTitle("Scatter chart Predictions");
        configurationscatterChart.setExporting(true);
        configurationscatterChart.getxAxis().setTitle("Date");
        configurationscatterChart.getyAxis().setTitle("Stock Price");

        configurationbarChart.setTitle("Bar chart Predictions");
        configurationbarChart.setExporting(true);
        configurationbarChart.getxAxis().setTitle("Date");
        configurationbarChart.getyAxis().setTitle("Stock Price");

        chartLayout1.addAndExpand(lineChart, scatterChart, barChart);
        add(chartLayout1);
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
                    model.lstmModel(file.getAbsolutePath(), memoryBuffer, lineChart, scatterChart, barChart);


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
