package com.example.app.Views.Logout;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "Logout")
@PageTitle("Logout")
public class LogoutView {

    /**
     *
     * Getting current session, and setting to null.
     */
    public LogoutView() {
        VaadinSession session = VaadinSession.getCurrent();
        session.setAttribute("username", null);
    }
}
