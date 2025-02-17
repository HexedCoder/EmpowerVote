package AdminGUI.adminGUIsupport;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Rectangle2D;

/**
 * Custom JPanel that renders a gradient background and an image header.
 */
public class Header extends javax.swing.JPanel {

    // Variables declaration
    private javax.swing.JLabel jLabel1;

    /**
     * Creates new form Header
     */
    public Header() {
        initComponents();
        setOpaque(false);
    } // End Header

    /**
     * Paints the component with a gradient background and an image header.
     * @param graphics The Graphics object to paint with.
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();

        // Set up gradient paint for the background
        g2.setPaint(new LinearGradientPaint(0, 0, 0, getHeight(),
                new float[]{0.0f, 0.5f, 1.0f}, // Positions of the colors
                new Color[]{Color.RED, Color.WHITE, new Color(30, 95, 156)} // Colors at each position
        ));

        // Fill the background with the gradient
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));

        // Dispose of the graphics object
        g2.dispose();

        super.paintComponent(graphics);
    } // End paintComponent

    /**
     * Initializes the components for the Header panel, including the label.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        // Set properties for the label
        jLabel1.setBackground(new java.awt.Color(30, 95, 156));
        jLabel1.setForeground(new java.awt.Color(30, 95, 156));

        // Set the image for the label
        jLabel1.setIcon(new javax.swing.ImageIcon("resources/banner6.png")); // NOI18N

        // Layout the components
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    } // End initComponents
} // End Header