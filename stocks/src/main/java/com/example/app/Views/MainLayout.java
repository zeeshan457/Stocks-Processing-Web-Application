package com.example.app.Views;

import com.example.app.Data.Service.AuthService;
import com.example.app.components.appnav.AppNav;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;


public class MainLayout extends AppLayout {

    /**
     *
     * Attributes and injecting
     */
    @Autowired
    private AuthService service;
    VaadinSession session = VaadinSession.getCurrent();
    private H2 viewTitle;


    /**
     *
     * @param service injecting service here
     */
    public MainLayout(AuthService service) {
        this.service = service;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    /**
     *
     * Add header content
     *
     */
    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");
        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        addToNavbar(true, toggle, viewTitle);
    }

    /**
     *
     * Add drawer content
     */
    private void addDrawerContent() {
        H1 appName = new H1("Stocks Application");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);
        Scroller scroller = new Scroller(createNavigation());
        addToDrawer(header, scroller, createFooter());
    }


    /**
     *
     * Creating navigation and calling method to process views for a specific role.
     * @return the nav based on the service call
     */
    public AppNav createNavigation() {
        String username = (String) session.getAttribute("username");
        AppNav nav = new AppNav();
        service.validateUserViews(username, nav);
        return nav;
    }

    /**
     *
     * @return the footer layout
     */
    private Footer createFooter() {
        Footer layout = new Footer();
        return layout;
    }

    /**
     *  sets text of current page title
     */
    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    /**
     *
     * @return the current title
     */
    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
