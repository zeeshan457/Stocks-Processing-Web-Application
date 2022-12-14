package com.example.app.Data.Service;

import com.example.app.Data.Entity.UserEntity;
import com.example.app.Data.Repository.UserRepository;
import com.example.app.Data.Role;
import com.example.app.Views.About.AboutView;
import com.example.app.Views.Home.HomeView;
import com.example.app.Views.Login.LoginView;
import com.example.app.Views.ManageStocks.ManageStocksView;
import com.example.app.Views.Processing.ProcessingView;
import com.example.app.Views.ViewStocks.ViewStocksView;
import com.example.app.components.appnav.AppNav;
import com.example.app.components.appnav.AppNavItem;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.security.auth.message.AuthException;
import java.util.List;

@Service
public class AuthService {

    /**
     *
     * Attributes and injections
     */
    @Autowired
    private UserRepository repo;
    private UserEntity user;
    public VaadinSession session;

    /**
     *
     * @param username passing username, to get username of user
     * @param password passing password, to get password of user
     * @throws AuthException throws exception, if credentials are incorrect
     */
    public void Login(String username, String password) throws AuthException {
        user = repo.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session = VaadinSession.getCurrent();
            session.setAttribute("username", username);
        } else {
            throw new AuthException();
        }
    }

    /**
     *
     * Register users here by calling the .save() method and checking if a username already exists
     *
     * @param username passing username, to get username text
     * @param password passing password, to get password text
     */
    public void Register(String username, String password) {
        UserEntity findUser = repo.findByUsername(username);
        if (findUser != null && findUser.getUsername().equals(username)) {
            Notification message = Notification.show("Username was taken");
            message.addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else {
            UserEntity user = new UserEntity(username, password, Role.USER);
            repo.save(user);
            Notification registerMessage = Notification.show("Registration Success");
            registerMessage.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            UI.getCurrent().navigate("Login");
        }
    }

    /**
     *
     * @return all usernames from database table
     */
    public List<String> getAllUsername() {
        return repo.getUsernames();
    }

    /**
     *
     * Add views based on the users' role.
     *
     * @param username passing username to get the role of that specific user.
     * @param nav adding nav items specific to the role.
     */
    public void validateUserViews(String username, AppNav nav) {
        user = repo.findByUsername(username);
        if (user.getRoles().equals(Role.ADMIN)) {
            nav.addItem(new AppNavItem("Home", HomeView.class, "la la-globe"));
            nav.addItem(new AppNavItem("Manage", ManageStocksView.class, "la la-columns"));
            nav.addItem(new AppNavItem("Logout", LoginView.class));
        } else if (user.getRoles().equals(Role.USER)) {
            nav.addItem(new AppNavItem("Home", HomeView.class, "la la-globe"));
            nav.addItem(new AppNavItem("View Stocks", ViewStocksView.class, "la la-columns"));
            nav.addItem(new AppNavItem("Processing", ProcessingView.class, "la la-code-branch"));
            nav.addItem(new AppNavItem("About", AboutView.class, "la la-file"));
            nav.addItem(new AppNavItem("Logout", LoginView.class));
        }
    }
}
