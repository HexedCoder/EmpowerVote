package LoginGUI.java.component;

import EmpowerVoteClient.LanguageManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.*;

import LoginGUI.java.support.ButtonOutLine;
import net.miginfocom.swing.MigLayout;


/**
 * PanelCover provides the user interface for the login/registration screen with customizable components
 * such as labels, buttons, and a language dropdown.
 *
 * This class includes event handling, custom component layout, and a gradient background.
 *
 * CMSC 495
 * @author Marc
 */
public class PanelCover extends javax.swing.JPanel {

    private final DecimalFormat df = new DecimalFormat("##0.###");
    private ActionListener event;
    private MigLayout layout;
    private JLabel title;
    private JLabel description1;
    private JLabel description2;
    private ButtonOutLine button;
    private boolean isLogin;
    private JButton cmdExit;

    /**
     * Constructs the PanelCover and initializes its components.
     * Sets the layout, creates labels, buttons, and a language dropdown.
     */
    public PanelCover() {
        setOpaque(false);
        layout = new MigLayout("wrap, fill", "[center]", "push[]25[]10[]25[]push");
        setLayout(layout);
        init();
    } // PanelCover

    /**
     * Initializes the components of the PanelCover.
     * Sets up the title, description labels, buttons, and the language dropdown.
     */
    private void init(){
        title = new JLabel("Welcome!");
        title.setFont(new Font("sansserif", 1, 30));
        title.setForeground(new Color(245,245,245));
        add(title);

        description1 = new JLabel("Sign in and make your voice heard!");
        description1.setForeground(new Color(245,245,245));
        add(description1);

        description2 = new JLabel("Vote today!");
        description2.setForeground(new Color(245,245,245));
        add(description2);

        button = new ButtonOutLine();
        button.setBackground(new Color(255,255,255));
        button.setForeground(new Color(255,255,255));
        button.setText("Sign In");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                event.actionPerformed(e);
            }
        });
        add(button, "w 65%, h 45");

        //-------------------------------------------------
        // language drop down here
        //=================================================
        String[] languages = {"English", "Spanish", "Russian"};
        JComboBox<String> comboBox = new JComboBox<>(languages);

        // Customize the ComboBox appearance
        comboBox.setFont(new Font("sansserif", 1, 12)); // Set font size
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 1)); // Add a black outline
        comboBox.setOpaque(false); // Make the combo box non-opaque
        comboBox.setBackground(new Color(0, 0, 0, 0)); // Set fully transparent background
        comboBox.setForeground(new Color(0,0,0)); // Set text color
        comboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Custom cell renderer for dropdown to make it transparent
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setForeground(new Color(0,0,0)); // Set text color to white
                return label;
            }
        });
        // Attach listener to update language
        comboBox.addActionListener(e -> {
            int selectedIndex = comboBox.getSelectedIndex();
            LanguageManager.getInstance().setLanguageIndex(selectedIndex);
            
            login(isLogin);
            
        });

        add(comboBox, "w 40%, h 10");
        //=================================================
        //-------------------------------------------------
        //-------------------------------------------------
        // Exit button
        //==================================================
        cmdExit = new JButton("Exit");
        cmdExit.setFont(new Font("sansserif", Font.BOLD, 14)); // Bigger text
        cmdExit.setForeground(new Color(0,0,0)); // Text color (black)
        cmdExit.setPreferredSize(new Dimension(140, 35)); // Bigger button
        cmdExit.setMargin(new Insets(10, 20, 10, 20)); // More padding
        cmdExit.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2)); // Thicker border
        cmdExit.setContentAreaFilled(false); // Transparent background
        cmdExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdExit.addActionListener(e -> System.exit(0));
        add(cmdExit);
        //=================================================
        //-------------------------------------------------
    } // init

    /**
     * Custom paint method to create a gradient background for the panel.
     * The gradient goes from blue to white to red.
     */
    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;

        // Define the colors and their positions for the gradient
        Color color1 = new Color(22, 16, 131); // rgb(22,16,131) - Blue
        Color color2 = new Color(254, 254, 255); // rgb(254,254,255) - White
        Color color3 = new Color(255, 0, 0); // rgb(255,0,0) - Red

        // Create a LinearGradientPaint with multiple color stops for a vertical gradient
        LinearGradientPaint gradient = new LinearGradientPaint(
                0, 0, 0, getHeight(), // Vertical gradient (top to bottom)
                new float[]{0.38f, 0.64f, 0.88f}, // Color stops at 38%, 70%, 88%
                new Color[]{color1, color2, color3} // Corresponding colors
        );

        // Apply the gradient paint
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Call the superclass method to maintain proper rendering behavior
        super.paintComponent(grphcs);
    } // paintComponent

    /**
     * Adds an event listener for button actions.
     *
     * @param event the action listener to be added to the button
     */
    public void addEvent(ActionListener event){
        this.event = event;
    } // addEvent

    /**
     * Registers the left alignment with a specified vertical offset.
     *
     * @param v the vertical offset value
     */
    public void registerLeft(double v){
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description1, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description2, "pad 0 -" + v + "% 0 0");
    } // registerLeft

    /**
     * Registers the right alignment with a specified vertical offset.
     *
     * @param v the vertical offset value
     */
    public void registerRight(double v){
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description1, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description2, "pad 0 -" + v + "% 0 0");
    } // registerRight

    /**
     * Configures the login panel with left alignment and a specified vertical offset.
     *
     * @param v the vertical offset value
     */
    public void loginLeft(double v){
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description1, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description2, "pad 0 " + v + "% 0 " + v + "%");
    } // loginLeft

    /**
     * Configures the login panel with right alignment and a specified vertical offset.
     *
     * @param v the vertical offset value
     */
    public void loginRight(double v){
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description1, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description2, "pad 0 " + v + "% 0 " + v + "%");
    } // loginRight

    /**
     * Updates the login state and changes the text of the labels and button accordingly.
     *
     * @param login true for login mode, false for registration mode
     */
    //-------------------------------------------
    //Language support
    //-------------------------------------------
    private void login(boolean login) {
    
        int languageIndex = LanguageManager.getInstance().getLanguageIndex(); // Get current language
        
        // Language-specific messages for login/register toggle
        String[][] messages = {
            // English
            
            {"Welcome!", "Sign in and make your voice heard!", "Vote today!", "Sign In", "Exit"},
            {"Need to Register?", "Create an Account", "in just a few easy steps!", "Register", "Exit"},
            
            // Spanish
            {"¡Bienvenido!", "¡Inicia sesión y haz que se escuche tu voz!", "¡Vota hoy!", "Iniciar sesión", "Salir"},
            {"¿Necesitas registrarte?", "Crea una cuenta", "en solo unos pocos pasos fáciles!", "Registrarse", "Salir"},
            
            // Russian
            {"Добро пожаловать!", "Войдите и дайте услышать свой голос!", "Голосуйте сегодня!", "Войти", "Выход"},
            {"Зарегистрироваться?", "Создайте аккаунт", "всего за несколько простых шагов!", "Зарегистрироваться", "Выход"}
        };

        // Select the correct row based on login/register state
        String[] selectedMessages = messages[languageIndex * 2 + (login ? 1 : 0)];

        // Update UI components
        title.setText(selectedMessages[0]);
        description1.setText(selectedMessages[1]);
        description2.setText(selectedMessages[2]);
        button.setText(selectedMessages[3]);
        cmdExit.setText(selectedMessages[4]);

        this.isLogin = login;
        
    } // login
} // PanelCover
