package LoginGUI.java.support;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;


/**
 * MyTextField is a custom JTextField that supports hint text and icons.
 * It customizes the appearance of the text field with icons and hint text when the field is empty.
 *
 * @author Marc
 */
public class MyTextField extends JTextField {

    private Icon prefixIcon;
    private Icon suffixIcon;
    private String hint = "";

    /**
     * Gets the hint text displayed when the text field is empty.
     *
     * @return the hint text
     */
    public String getHint() {
        return hint;
    } // getHint

    /**
     * Sets the hint text to be displayed when the text field is empty.
     *
     * @param hint the hint text to set
     */
    public void setHint(String hint) {
        this.hint = hint;
    } // setHint

    /**
     * Gets the prefix icon (e.g., an icon on the left side of the text field).
     *
     * @return the prefix icon
     */
    public Icon getPrefixIcon() {
        return prefixIcon;
    } // getPrefixIcon

    /**
     * Sets the prefix icon (e.g., an icon to the left of the text).
     *
     * @param prefixIcon the prefix icon to set
     */
    public void setPrefixIcon(Icon prefixIcon) {
        this.prefixIcon = prefixIcon;
        initBorder(); // Recalculate border after icon change
    } // setPrefixIcon

    /**
     * Gets the suffix icon (e.g., an icon on the right side of the text field).
     *
     * @return the suffix icon
     */
    public Icon getSuffixIcon() {
        return suffixIcon;
    } // getSuffixIcon

    /**
     * Sets the suffix icon (e.g., an icon to the right of the text).
     *
     * @param suffixIcon the suffix icon to set
     */
    public void setSuffixIcon(Icon suffixIcon) {
        this.suffixIcon = suffixIcon;
        initBorder(); // Recalculate border after icon change
    } // setSuffixIcon

    /**
     * Constructs a MyTextField with custom properties for background, font, and selection color.
     */
    public MyTextField() {
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(0, 0, 0, 0));
        setForeground(Color.decode("#7A8C8D"));
        setFont(new java.awt.Font("sansserif", 0, 13));
        setSelectionColor(new Color(75, 175, 152));
    } // MyTextField

    /**
     * Custom paint method to draw the background and icons for the text field.
     *
     * @param g the graphics context used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(245, 224, 220)); // Set background color
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5); // Draw rounded rectangle
        paintIcon(g); // Paint the icons if available
        super.paintComponent(g); // Call the superclass's paint method for default behavior
    } // paintComponent

    /**
     * Paints the prefix and suffix icons if they are set.
     *
     * @param g the graphics context used for painting
     */
    private void paintIcon(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (prefixIcon != null) {
            Image prefix = ((ImageIcon) prefixIcon).getImage();
            int y = (getHeight() - prefixIcon.getIconHeight()) / 2; // Center the icon vertically
            g2.drawImage(prefix, 10, y, this); // Draw the prefix icon
        }
        if (suffixIcon != null) {
            Image suffix = ((ImageIcon) suffixIcon).getImage();
            int y = (getHeight() - suffixIcon.getIconHeight()) / 2; // Center the icon vertically
            g2.drawImage(suffix, getWidth() - suffixIcon.getIconWidth() - 10, y, this); // Draw the suffix icon
        }
    } // paintIcon

    /**
     * Paints the hint text when the text field is empty.
     *
     * @param g the graphics context used for painting
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (getText().length() == 0) {
            int h = getHeight();
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Insets ins = getInsets();
            FontMetrics fm = g.getFontMetrics();
            g.setColor(new Color(200, 200, 200)); // Hint text color
            g.drawString(hint, ins.left, h / 2 + fm.getAscent() / 2 - 2); // Draw hint text
        }
    } // paint

    /**
     * Initializes the border based on the icons.
     */
    private void initBorder() {
        int left = 15;
        int right = 15;

        // Adjust left padding if prefix icon is set
        if (prefixIcon != null) {
            left = prefixIcon.getIconWidth() + 15;
        }
        // Adjust right padding if suffix icon is set
        if (suffixIcon != null) {
            right = suffixIcon.getIconWidth() + 15;
        }
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, left, 10, right)); // Set the new border
    } // initBorder
} // MyTextField