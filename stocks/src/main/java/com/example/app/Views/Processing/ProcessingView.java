package com.example.app.Views.Processing;

import com.example.app.Views.MainLayout;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private FileOutputStream fos = null;

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
//        HorizontalLayout chartLayout2 = new HorizontalLayout();
        Chart lineChart = new Chart(ChartType.LINE);
        Chart scatterChart = new Chart(ChartType.SCATTER);
        Chart barChart = new Chart(ChartType.BAR);

        Configuration configurationlineChart = lineChart.getConfiguration();
        Configuration configurationscatterChart = scatterChart.getConfiguration();
        Configuration configurationbarChart = barChart.getConfiguration();


        DataSeries series = new DataSeries();
        series.add(new DataSeriesItem(0, 10));
        series.add(new DataSeriesItem(1, 20));
        series.add(new DataSeriesItem(2, 15));

        configurationlineChart.setTitle("Line chart prediction");
        configurationlineChart.setExporting(true);
        configurationlineChart.addSeries(series);

        configurationscatterChart.setTitle("Scatter chart prediction");
        configurationscatterChart.setExporting(true);
        configurationscatterChart.addSeries(series);

        configurationbarChart.setTitle("Bar chart prediction");
        configurationbarChart.setExporting(true);
        configurationbarChart.addSeries(series);

        chartLayout1.addAndExpand(lineChart, scatterChart, barChart);
        add(chartLayout1);
    }



    /**
     *
     *
     * Action events.
     * Server side handling and listeners.
     *
     */
    public void actionEvents() {
        upload.addSucceededListener(event -> {
            // get file
            File file = new File(event.getFileName());
            Notification notification = Notification.show(file.getName()
                    + "\n " + file.getAbsolutePath());
            notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);

            try {
                // Use opencsv to parse the CSV data
                Reader reader = new InputStreamReader(memoryBuffer.getInputStream());
                CSVReader csvReader = new CSVReader(reader);
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    // Extract the columns for date, open, close, high, and low
                    String date = line[0];
                    String open = line[1];
                    String close = line[2];
                    String high = line[3];
                    String low = line[4];

                }

            } catch (IOException | CsvValidationException e) {
                Notification.show("IOException || CsvValidationException");
            }
        });

        upload.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();
            Notification notification = Notification.show(errorMessage + " Files must be .CSV");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });



        processButton.addClickListener(event -> {

            Notification notification = Notification.show("test");

        });
    }
}
