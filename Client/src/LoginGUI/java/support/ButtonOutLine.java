package LoginGUI.java.support;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;


/**
 * ButtonOutLine is a custom JButton that draws an outlined button with rounded corners.
 * It provides a visual effect with antialiasing for smooth edges.
 *
 * @author Marc
 */
public class ButtonOutLine extends JButton {

    /**
     * Constructs a ButtonOutLine with no background and a white color.
     */
    public ButtonOutLine() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(5, 0, 5, 0));
        setBackground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    } // ButtonOutLine

    /**
     * Custom painting method for drawing a rounded outline on the button.
     *
     * @param graphics the graphics context to use for painting
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable antialiasing
        g2.setColor(getBackground()); // Use background color for the outline
        g2.drawRoundRect(0, 0, width - 1, height - 1, height, height); // Draw rounded outline

        // Dynamically adjust font size based on button height
        int fontSize = Math.max(12, height / 3); // Minimum font size 12, scales with height
        setFont(new Font("sansserif", Font.BOLD, fontSize));
        
        super.paintComponent(graphics); // Call the superclasses paint method for default rendering
    } // paintComponent
} // ButtonOutLine
