package ch.bbzbl.time.navigation;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.internal.StateTree;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.Router;
import com.vaadin.flow.server.VaadinService;

import java.util.Optional;

/**
 * A menu item for the {@link NavigationItem} component.
 * <p>
 * Can contain a label and/or an icon and links to a given {@code path}.
 */
@JsModule("@vaadin-component-factory/vcf-nav")
@Tag("vcf-nav-item")
public class NavigationMenu extends Component {

    /**
     * Creates a menu item which does not link to any view but only shows the given
     * label.
     * 
     * @param label
     *            the label for the item
     */
    public NavigationMenu(String label) {
        setLabel(label);
    }

    /**
     * Creates a new menu item using the given label that links to the given path.
     * 
     * @param label
     *            the label for the item
     * @param path
     *            the path to link to
     */
    public NavigationMenu(String label, String path) {
        setPath(path);
        setLabel(label);
    }

    /**
     * Creates a new menu item using the given label that links to the given view.
     * 
     * @param label
     *            the label for the item
     * @param view
     *            the view to link to
     */
    public NavigationMenu(String label, Class<? extends Component> view) {
        setPath(view);
        setLabel(label);
    }

    /**
     * Creates a new menu item using the given label and icon that links to the
     * given path.
     * 
     * @param label
     *            the label for the item
     * @param path
     *            the path to link to
     * @param icon
     *            the icon for the item
     */
    public NavigationMenu(String label, String path, Component icon) {
        setPath(path);
        setLabel(label);
        setIcon(icon);
    }

    /**
     * Creates a new menu item using the given label that links to the given view.
     * 
     * @param label
     *            the label for the item
     * @param view
     *            the view to link to
     * @param icon
     *            the icon for the item
     */
    public NavigationMenu(String label, Class<? extends Component> view, Component icon) {
        setPath(view);
        setLabel(label);
        setIcon(icon);
    }

    public NavigationMenu() {

    }

    /**
     * Adds menu item(s) inside this item, creating a hierarchy.
     * 
     * @param navigationMenus
     *            the menu item(s) to add
     * @return this item for chaining
     */
    public NavigationMenu addItem(NavigationMenu... navigationMenus) {
        for (NavigationMenu navigationMenu : navigationMenus) {
            navigationMenu.getElement().setAttribute("slot", "children");
            getElement().appendChild(navigationMenu.getElement());
        }

        return this;
    }

    /**
     * Removes the given menu item from this item.
     * <p>
     * If the given menu item is not a child of this menu item, does nothing.
     * 
     * @param navigationMenu
     *            the menu item to remove
     * @return this item for chaining
     */
    public NavigationMenu removeItem(NavigationMenu navigationMenu) {
        Optional<Component> parent = navigationMenu.getParent();
        if (parent.isPresent() && parent.get() == this) {
            getElement().removeChild(navigationMenu.getElement());
        }

        return this;
    }

    /**
     * Removes all menu items from this item.
     * 
     * @return this item for chaining
     */
    public NavigationMenu removeAllItems() {
        getElement().removeAllChildren();
        return this;
    }

    /**
     * Gets the label for the item.
     * 
     * @return the label or null if no label has been set
     */
    public String getLabel() {
        return getExistingLabelElement().map(e -> e.getText()).orElse(null);
    }

    /**
     * Set a textual label for the item.
     * <p>
     * The label is also available for screen rader users.
     * 
     * @param label
     *            the label to set
     * @return this instance for chaining
     */
    public NavigationMenu setLabel(String label) {
        getLabelElement().setText(label);
        return this;
    }

    private Optional<Element> getExistingLabelElement() {
        return getElement().getChildren().filter(child -> !child.hasAttribute("slot")).findFirst();
    }

    private Element getLabelElement() {
        return getExistingLabelElement().orElseGet(() -> {
            Element element = Element.createText("");
            getElement().appendChild(element);
            return element;
        });
    }

    /**
     * Sets the path this item links to.
     * 
     * @param path
     *            the path to link to
     * @return this instance for chaining
     */
    public NavigationMenu setPath(String path) {
        getElement().setAttribute("path", path);
        return this;
    }

    /**
     * Sets the view this item links to.
     * 
     * @param view
     *            the view to link to
     * @return this instance for chaining
     */
    public NavigationMenu setPath(Class<? extends Component> view) {
        String url = RouteConfiguration.forRegistry(getRouter().getRegistry()).getUrl(view);
        setPath(url);
        return this;
    }

    private Router getRouter() {
        Router router = null;
        if (getElement().getNode().isAttached()) {
            StateTree tree = (StateTree) getElement().getNode().getOwner();
            router = tree.getUI().getInternals().getRouter();
        }
        if (router == null) {
            router = VaadinService.getCurrent().getRouter();
        }
        if (router == null) {
            throw new IllegalStateException("Implicit router instance is not available. "
                    + "Use overloaded method with explicit router parameter.");
        }
        return router;
    }

    public String getPath() {
        return getElement().getAttribute("path");
    }

    private int getIconElementIndex() {
        for (int i = 0; i < getElement().getChildCount(); i++) {
            if ("prefix".equals(getElement().getChild(i).getAttribute("slot"))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Sets the icon for the item.
     * <p>
     * Can also be used to set a custom component to be shown in front of the label.
     * 
     * @param icon
     *            the icon to show
     * @return this instance for chaining
     */
    public NavigationMenu setIcon(Component icon) {
        icon.getElement().setAttribute("slot", "prefix");
        int iconElementIndex = getIconElementIndex();
        if (iconElementIndex != -1) {
            getElement().setChild(iconElementIndex, icon.getElement());
        } else {
            getElement().appendChild(icon.getElement());
        }
        return this;
    }

    /**
     * Sets the expanded status of the item.
     *
     * @param value
     *            true to expand the item, false to collapse it
     */
    public NavigationMenu setExpanded(boolean value) {
        if (value) {
            getElement().setAttribute("expanded", "");
        } else {
            getElement().removeAttribute("expanded");
        }
        return this;
    }

}
