package AdminGUI.menu;

/**
 * Interface to handle menu item selection events.
 * This interface is implemented to define actions that should occur when a menu item is selected.
 *
 * @author Marc
 */
public interface MenuEvent {

    /**
     * Method to be called when a menu item is selected.
     *
     * @param index The index of the main menu item.
     * @param subIndex The index of the sub-menu item, if applicable.
     */
    public void selected(int index, int subIndex);
} // MenuEvent