package AdminGUI.adminGUIchart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 * Custom JLabel used in the polar area chart to display labels with a circular background.
 */
public class PolarAreaLabel extends JLabel {

    // Constructor to initialize the PolarAreaLabel with custom settings.
    public PolarAreaLabel() {
        setBorder(new EmptyBorder(3, 25, 3, 3));  // Set custom padding for the label
        setFont(getFont().deriveFont(0, 13));  // Set custom font size
        setForeground(new Color(100, 100, 100));  // Set text color
    } // End constructor

    /**
     * Override paint method to customize the label rendering with a circular background.
     */
    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);  // Call the superclass paint method for basic label rendering
        int size = getHeight() - 10;  // Set the size of the circle based on label height
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  // Enable anti-aliasing
        int y = (getHeight() - size) / 2;  // Calculate the Y position to center the circle vertically
        g2.setColor(getBackground());  // Set the background color for the circle
        g2.fillOval(3, y, size, size);  // Draw the filled circle with custom position and size
        g2.dispose();  // Dispose of the Graphics2D object to release resources
    } // End paint
} // End PolarAreaLabel