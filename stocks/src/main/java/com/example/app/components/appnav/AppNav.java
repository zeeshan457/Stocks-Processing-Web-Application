package com.example.app.components.appnav;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.dom.Element;
import java.util.Optional;

/**
 * A navigation menu with support for hierarchical and flat menus.
 * <p>
 * Items can be added using {@link #addItem(AppNavItem)} and hierarchy can be
 * created by adding {@link AppNavItem} instances to other {@link AppNavItem}
 * instances.
 */
@JsModule("@vaadin-component-factory/vcf-nav")
@Tag("vcf-nav")
public class AppNav extends Component implements HasSize, HasStyle {

    /**
     * Creates a new menu.
     */
    public AppNav() {
    }

    /**
     *
     * @param label
     *            the label to use
     */
    public AppNav(String label) {
        setLabel(label);
    }

    /**
     * Adds menu item(s) to the menu.
     *
     * @param appNavItems
     *            the menu item(s) to add
     * @return the menu for chaining
     */
    public AppNav addItem(AppNavItem... appNavItems) {
        for (AppNavItem appNavItem : appNavItems) {
            getElement().appendChild(appNavItem.getElement());
        }

        return this;
    }

    /**
     * Removes the menu item from the menu.
     *
     * @param appNavItem
     * @return the menu for chaining
     */
    public AppNav removeItem(AppNavItem appNavItem) {
        Optional<Component> parent = appNavItem.getParent();
        if (parent.isPresent() && parent.get() == this) {
            getElement().removeChild(appNavItem.getElement());
        }

        return this;
    }

    /**
     *
     * @return this item for chaining
     */
    public AppNav removeAllItems() {
        getElement().removeAllChildren();
        return this;
    }

    /**
     * Gets the textual label for the navigation.
     *
     * @return the label or null if no label has been set
     */
    public String getLabel() {
        return getExistingLabelElement().map(e -> e.getText()).orElse(null);
    }

    /**
     *
     * @param label
     *            the label to set
     * @return this instance for chaining
     */
    public AppNav setLabel(String label) {
        getLabelElement().setText(label);
        return this;
    }

    private Optional<Element> getExistingLabelElement() {
        return getElement().getChildren().filter(child -> "label".equals(child.getAttribute("slot"))).findFirst();
    }

    private Element getLabelElement() {
        return getExistingLabelElement().orElseGet(() -> {
            Element element = new Element("span");
            element.setAttribute("slot", "label");
            getElement().appendChild(element);
            return element;
        });
    }

    /**
     *
     * @return true if the menu is collapsible, false otherwise
     */
    public boolean isCollapsible() {
        return getElement().hasAttribute("collapsible");
    }

    /**

     * NOTE: The navigation has to have a label for it to be collapsible.
     *
     * @param collapsible
     * @return this instance for chaining
     */
    public AppNav setCollapsible(boolean collapsible) {
        getElement().setAttribute("collapsible", "");
        return this;
    }

}
