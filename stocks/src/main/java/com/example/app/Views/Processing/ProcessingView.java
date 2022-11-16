package com.example.app.Views.Processing;

import com.example.app.Views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

// Annotations for this class
@PageTitle("Processing")
@Route(value = "Processing", layout = MainLayout.class)
public class ProcessingView extends VerticalLayout {

    // Attributes
    Upload upload;
    Button processButton;
    Button uploadButton;

    // Constructor and method calls
    public ProcessingView() {
        addFileOpener();
        actionEvents();
    }

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

        upload.setWidthFull();

        buttonLayout.addAndExpand(processButton);

        add(upload, buttonLayout);

        // Configs
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.START);
        //getStyle().set("text-align", "center");
    }

    public void actionEvents() {
        upload.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();
            Notification notification = Notification.show(errorMessage + " Files must be .CSV");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });

    }

}
