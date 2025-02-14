package LoginGUI.java.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
        add(button, "w 60%, h 40");

        //-------------------------------------------------
        // language drop down here
        //=================================================
        String[] languages = {"English", "Spanish", "Russian"};
        JComboBox<String> comboBox = new JComboBox<>(languages);

        // Customize the ComboBox appearance
        comboBox.setFont(new Font("sansserif", 1, 12)); // Set font size
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1)); // Add a black outline
        comboBox.setOpaque(false); // Make the combo box non-opaque
        comboBox.setBackground(new Color(0, 0, 0, 0)); // Set fully transparent background
        comboBox.setForeground(new Color(100,100,100)); // Set text color
        comboBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Custom cell renderer for dropdown to make it transparent
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setForeground(new Color(100,100,100)); // Set text color to white
                return label;
            }
        });

        add(comboBox, "w 30%, h 8");
        //=================================================
        //-------------------------------------------------
        //-------------------------------------------------
        // Exit button
        //==================================================
        JButton cmdExit = new JButton("Exit");
        cmdExit.setForeground(new Color(100,100,100));
        cmdExit.setFont(new Font("sansserif", 1, 12));
        cmdExit.setContentAreaFilled(false);
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
    private void login(boolean login){
        if (this.isLogin != login){
            if (login){
                title.setText("Need to Register?");
                description1.setText("Create an Account");
                description2.setText("in just a few easy steps!");
                button.setText("Register");
            } else {
                title.setText("Welcome!");
                description1.setText("Sign in and make your voice heard!");
                description2.setText("Vote today!");
                button.setText("Sign In");
            }
            this.isLogin = login;
        }
    } // login
} // PanelCover
