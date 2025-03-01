package menu;

import EmpowerVoteClient.LanguageManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 * Menu class that handles the rendering and interactions for the main menu.
 * Supports submenus, localization, and an exit button.
 * @author Marc
 */
public class Menu extends JComponent implements LanguageManager.LanguageChangeListener { // Implements listener
    
    private JButton cmdExit; // Declare exit button
    private MenuEvent event;
    private MigLayout layout;
    private String[][] menuItems;

    /**
     * Constructor for Menu class.
     */
    public Menu(){
        LanguageManager.getInstance().addListener(this); // Register for language changes
        
        // Initialize the exit button BEFORE updating menu items
        cmdExit = new JButton();
        cmdExit.setForeground(new Color(250,250,250));
        cmdExit.setBackground(new Color(200, 0, 0)); // Red background for visibility
        cmdExit.setFont(new Font("sansserif", Font.BOLD, 20));
        cmdExit.setContentAreaFilled(true);
        cmdExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdExit.addActionListener(e -> System.exit(0));

        // Now update menu items
        updateMenuItems(LanguageManager.getInstance().getLanguageIndex());

        // Initialize UI
        init();
    }

    /**
     * Initializes the menu and sets up menu items and their actions.
     */
    private void init(){
        layout = new MigLayout("Wrap 1, fillx, gapy 5, inset 2", "fill");
        setLayout(layout);
        setOpaque(true);

        removeAll(); // Clear existing components

        // Initialize menu items
        for(int i = 0; i < menuItems.length; i++){
            addMenu(menuItems[i][0], i);
        }

        // Add exit button to the layout
        add(cmdExit, "push, aligny bottom");
    }

    /**
     * Adds a menu item and sets its action.
     *
     * @param menuName The name of the menu item.
     * @param index    The index of the menu item.
     */
    private void addMenu(String menuName, int index){
        int length = menuItems[index].length;
        MenuItem item = new MenuItem(menuName, index, length > 1);

        item.addActionListener(e -> {
            if(length > 1){
                if(!item.isSelected()){
                    item.setSelected(true);
                    addSubMenu(item, index, length, getComponentZOrder(item));
                } else {
                    hideMenu(item, index);
                    item.setSelected(false);
                }
            } else {
                if(event != null){
                    event.selected(index, 0);
                }
            }
        });

        add(item);
        revalidate();
        repaint();
    }

    /**
     * Adds a submenu for a menu item.
     *
     * @param item    The menu item that triggered the submenu.
     * @param index   The index of the menu item.
     * @param length  The length of the submenu.
     * @param indexZorder The Z-order index for rendering.
     */
    private void addSubMenu(MenuItem item, int index, int length, int indexZorder){
        JPanel panel = new JPanel(new MigLayout("wrap 1, fillx, inset 0, gapy 0", "fill"));
        panel.setName(String.valueOf(index));
        panel.setOpaque(false);

        for(int i = 1; i < length; i++){
            MenuItem subItem = new MenuItem(menuItems[index][i], i, false);
            subItem.addActionListener(e -> {
                if (event != null){
                    event.selected(index, subItem.getIndex());
                }
            });
            subItem.initSubMenu(i, length);
            panel.add(subItem);
        }

        add(panel, "h 0!", indexZorder+1);
        revalidate();
        repaint();
        MenuAnimation.showMenu(panel, item, layout, true);
    }

    /**
     * Hides a submenu when the menu item is deselected.
     *
     * @param item  The menu item that triggered the hiding of the submenu.
     * @param index The index of the menu item.
     */
    private void hideMenu(MenuItem item, int index){
        for(Component com : getComponents()){
            if(com instanceof JPanel && com.getName() != null && com.getName().equals(String.valueOf(index))){
                com.setName(null);
                MenuAnimation.showMenu(com, item, layout, false);
                break;
            }
        }
    }

    /**
     * Paints the component with a background color.
     */
    @Override
    protected void paintComponent(Graphics grphcs){
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setColor(new Color(0,56,122));
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        super.paintComponent(grphcs);
    }

    /**
     * Updates menu items based on the selected language.
     *
     * @param languageIndex The index of the selected language.
     */
    private void updateMenuItems(int languageIndex) {
        // Language-specific menu items
        String[][][] localizedMenuItems = {
            // English
            {
                {"Main Page"},
                {"Local Government", "City Mayor", "City Council"},
                {"State Government", "Governor", "Senator"},
                {"Federal Government", "President", "Congress"},
                {"Review & Submit"}
            },
            // Spanish 
            {
                {"Página Principal"},
                {"Gob. Local", "Alcalde", "Concejo"},
                {"Gob. Estatal", "Gobernador", "Senador"},
                {"Gob. Federal", "Presidente", "Congreso"},
                {"Revisar y Enviar"}
            },
            // Russian 
            {
                {"Главная"},
                {"Местное", "Мэр", "Совет"},
                {"Штат", "Губернатор", "Сенатор"},
                {"Федеральное", "Президент", "Конгресс"},
                {"Проверить и Отправить"}
            }
        };

        // Exit button translations
        String[] exitLabels = {"Exit", "Salir", "Выход"};

        // Set menu items based on selected language
        menuItems = localizedMenuItems[languageIndex];

        // Ensure cmdExit exists before updating its text
        if (cmdExit != null) {
            cmdExit.setText(exitLabels[languageIndex]); // Update exit button text
        }

        // Refresh UI
        init();
        revalidate();
        repaint();
    }

    /**
     * Handles language changes and updates menu items accordingly.
     *
     * @param newIndex The new language index.
     */
    @Override
    public void onLanguageChange(int newIndex) {
        updateMenuItems(newIndex);
    }

    /**
     * Sets the menu event listener.
     *
     * @param event The event listener.
     */
    public void setEvent(MenuEvent event) {
        this.event = event;
    }

    /**
     * Gets the menu event listener.
     *
     * @return The menu event listener.
     */
    public MenuEvent getEvent() {
        return event;
    }

    /**
     * Adds a listener to the exit button.
     *
     * @param listener The ActionListener to add.
     */
    public void addExitButtonListener(ActionListener listener) {
        cmdExit.addActionListener(listener);
    }

    /**
     * Gets the exit button component.
     *
     * @return The exit button.
     */
    public JButton getCmdExit() {
        return cmdExit;
    }
}
