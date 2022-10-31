package com.example.app;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * Annotations for the applications configurations
 *
 */
@SpringBootApplication
@NpmPackage(value = "@fontsource/abel", version = "4.5.0")
@Theme(value = "stocks", variant = Lumo.DARK)
@PWA(name = "Stocks", shortName = "Stocks", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
//@EnableJpaRepositories("com.example.app.Data.Repository")
//@EntityScan("com.example.app.Data.Entity")

public class Application implements AppShellConfigurator {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
