package userGUIsupport;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Rectangle2D;

/**
 * Custom header panel with gradient background and an image.
 */
public class Header extends javax.swing.JPanel {

    // Instance variables
    private javax.swing.JLabel jLabel1;

    public Header() {
        initComponents();
        setOpaque(false);
    } // End Header constructor

    /**
     * Paints the custom gradient background for the header.
     *
     * @param grphcs The graphics context used for painting.
     */
    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setPaint(new LinearGradientPaint(0, 0, 0, getHeight(),
                new float[]{0.0f, 0.5f, 1.0f}, // Positions of the colors
                new Color[]{Color.RED, Color.WHITE, new Color(30, 95, 156)} // Colors at each position
        ));
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        g2.dispose();
        super.paintComponent(grphcs);
    } // End paintComponent

    /**
     * Initializes the components for the header.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() { // Start initComponents
        jLabel1 = new javax.swing.JLabel();

        jLabel1.setBackground(new java.awt.Color(30, 95, 156));
        jLabel1.setForeground(new java.awt.Color(30, 95, 156));

        jLabel1.setIcon(new javax.swing.ImageIcon("resources/banner6.png")); // NOI18N

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
} // End Header class