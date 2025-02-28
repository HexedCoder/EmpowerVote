package AdminGUI.menu;

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
import EmpowerVoteClient.LanguageManager;

/**
 * Menu class for the admin interface.
 * Handles the rendering and interaction of the menu components.
 */
public class Menu extends JComponent implements LanguageManager.LanguageChangeListener{
    private JButton cmdExit; // Declare exit button
    // Instance variables
    private MenuEvent event;
    private MigLayout layout;
    private String[][] menuItem;

    /**
     * Constructor to initialize the Menu.
     */
    public Menu(){
        LanguageManager.getInstance().addListener(this); // Register for language changes
        
        // Initialize the exit button BEFORE updating menu items
        cmdExit = new JButton();
        cmdExit.setForeground(new Color(250,250,250));
        cmdExit.setFont(new Font("sansserif", 1, 20));
        cmdExit.setContentAreaFilled(false);
        cmdExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdExit.addActionListener(e -> System.exit(0));

        // Now update menu items
        updateMenuItems(LanguageManager.getInstance().getLanguageIndex());

        // Initialize UI
        init();
    } // End Menu

    /**
     * Initializes the menu layout and components.
     */
    private void init() {
        layout = new MigLayout("Wrap 1, fillx, gapy 5, inset 2", "fill");
        setLayout(layout);
        setOpaque(true);

        // Initialize MenuItems
        for (int i = 0; i < menuItems.length; i++) {
            addMenu(menuItems[i][0], i);
        }

        // Add exit button to the layout
        add(cmdExit, "push, aligny bottom");
        
    } // End init

    /**
     * Adds a menu item to the menu.
     *
     * @param menuName The name of the menu item.
     * @param index The index of the menu item.
     */
    private void addMenu(String menuName, int index) {
        int length = menuItems[index].length;
        MenuItem item = new MenuItem(menuName, index, length > 1);

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (length > 1) {
                    if (!item.isSelected()) {
                        item.setSelected(true);
                        addSubMenu(item, index, length, getComponentZOrder(item));
                    } else {
                        hideMenu(item, index);
                        item.setSelected(false);
                    }
                } else {
                    if (event != null) {
                        event.selected(index, 0);
                    }
                }
            }
        });

        add(item);
        revalidate();
        repaint();
    } // End addMenu

    /**
     * Adds a submenu to the menu.
     *
     * @param item The menu item.
     * @param index The index of the menu.
     * @param length The number of submenu items.
     * @param indexZorder The z-order of the component.
     */
    private void addSubMenu(MenuItem item, int index, int length, int indexZorder) {
        JPanel panel = new JPanel(new MigLayout("wrap 1, fillx, inset 0, gapy 0", "fill"));
        panel.setName(index + "");
        panel.setOpaque(false);

        for (int i = 1; i < length; i++) {
            MenuItem subItem = new MenuItem(menuItems[index][i], i, false);
            subItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (event != null) {
                        event.selected(index, subItem.getIndex());
                    }
                }
            });
            subItem.initSubMenu(i, length);
            panel.add(subItem);
        }

        add(panel, "h 0!", indexZorder + 1);
        revalidate();
        repaint();
        MenuAnimation.showMenu(panel, item, layout, true);
    } // End addSubMenu

    /**
     * Hides the submenu of a menu item.
     *
     * @param item The menu item whose submenu should be hidden.
     * @param index The index of the menu.
     */
    private void hideMenu(MenuItem item, int index) {
        for (Component com : getComponents()) {
            if (com instanceof JPanel && com.getName() != null && com.getName().equals(index + "")) {
                com.setName(null);
                MenuAnimation.showMenu(com, item, layout, false);
                break;
            }
        }
    } // End hideMenu

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setColor(new Color(145, 20, 10));
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        super.paintComponent(graphics);
    } // End paintComponent

    //language support
    private void updateMenuItems(int languageIndex) {
        // Language-specific menu items
        String[][][] localizedMenuItems = {
            // English
            {
                {"Main Page"},
                {"Local Government", "City Mayor", "City Council"},
                {"State Government", "Governor", "Senator"},
                {"Federal Government", "President", "Congress"},
                {"Statistics"}
            },
            // Spanish (More Concise)
            {
                {"Página Principal"},
                {"Gob. Local", "Alcalde", "Concejo"},
                {"Gob. Estatal", "Gobernador", "Senador"},
                {"Gob. Federal", "Presidente", "Congreso"},
                {"Estadísticas"}
            },
            // Russian (More Concise)
            {
                {"Главная"},
                {"Местное", "Мэр", "Совет"},
                {"Штат", "Губернатор", "Сенатор"},
                {"Федеральное", "Президент", "Конгресс"},
                {"Статистика"}
            }
        };

        // Exit button translations
        String[] exitLabels = {"Exit", "Salir", "Выход"};

        menuItems = localizedMenuItems[languageIndex]; // Set menu items based on selected language
        
        // Ensure cmdExit exists before updating its text
        if (cmdExit != null) {
            cmdExit.setText(exitLabels[languageIndex]); // Update exit button text
        }

        revalidate();
        repaint(); // Refresh UI
    }

    @Override
    public void onLanguageChange(int newIndex) { // Correctly implementing interface method
        updateMenuItems(newIndex);
    }

    public MenuEvent getEvent() {
        return event;
    } // End getEvent

    public void setEvent(MenuEvent event) {
        this.event = event;
    } // End setEvent
} // End Menu
