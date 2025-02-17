package menu;

/**
 * Interface for handling menu item selection events.
 * Classes implementing this interface should define the behavior when a menu item is selected.
 *
 * @author Marc
 */
public interface MenuEvent {

    /**
     * This method is called when a menu item is selected.
     *
     * @param index    The index of the selected menu item.
     * @param subIndex The subindex of the selected item, if applicable.
     */
    void selected(int index, int subIndex);
} // End MenuEvent