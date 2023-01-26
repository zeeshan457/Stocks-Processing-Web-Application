package com.example.app.Views.Processing;

import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;

@PageTitle("Processing")
@Route(value = "Processing", layout = MainLayout.class)
public class ProcessingView extends VerticalLayout {

    /**
     *
     * Attributes
     *
     */
    private Upload upload;
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
        actionEvents();
    }

    /**
     *
     * Creating the file section
     *
     */
    public void addFileOpener() {
        setSpacing(true);
        upload = new Upload();
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

    /**
     *
     *
     * Action events.
     * Server side handling and listeners.
     *
     */
    public void actionEvents() {
        upload.setReceiver(new Receiver() {
            @Override
            public OutputStream receiveUpload(String filename, String mimeType) {
                try {
                    fos = new FileOutputStream("file path");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return fos;
            }
        });

        upload.addSucceededListener(event -> {
            // get file
            File file = new File(event.getFileName());
            Notification notification = Notification.show(file.getName()
                    + "\n " + file.getAbsolutePath());
            notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);

            try (Reader reader = new FileReader(file);
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
                for (CSVRecord csvRecord : csvParser) {
                    // Accessing values by the names assigned to each column
                    String Date = csvRecord.get("date");
                    String Open = csvRecord.get("open");
                    String Close = csvRecord.get("close");
                    String High = csvRecord.get("high");
                    String Low = csvRecord.get("low");

                    // adding values to some data structure
                }
            }
            catch (IOException e) {
                Notification error = Notification.show("IOException, can't read file contents.");
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
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
