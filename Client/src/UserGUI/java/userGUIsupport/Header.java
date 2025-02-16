
package UserGUI.java.userGUIsupport;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Marc
 */
public class Header extends javax.swing.JPanel {

   
    public Header() {
        initComponents();
        setOpaque(false);    
    }
    
    @Override
    protected void paintComponent(Graphics grphcs){
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setPaint(new LinearGradientPaint(0, 0, 0, getHeight(),
    new float[]{0.0f, 0.5f, 1.0f}, // Positions of the colors
    new Color[]{Color.RED, Color.WHITE, new Color(30, 95, 156)} // Colors at each position
));
        g2.fill(new Rectangle2D.Double(0,0,getWidth(), getHeight()));
        g2.dispose();
        super.paintComponent(grphcs);
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        jLabel1.setBackground(new java.awt.Color(30, 95, 156));
        jLabel1.setForeground(new java.awt.Color(30, 95, 156));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/banner6.png"))); // NOI18N

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
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
