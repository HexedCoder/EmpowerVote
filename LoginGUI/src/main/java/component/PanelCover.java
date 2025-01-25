
package component;

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
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import net.miginfocom.swing.MigLayout;
import support.ButtonOutLine;


/**
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
    
    public PanelCover() {
        initComponents();
        setOpaque(false);
        layout = new MigLayout("wrap, fill", "[center]", "push[]25[]10[]25[]push");
        setLayout(layout);
        init();
    }

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
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
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
    }


    
    public void addEvent(ActionListener event){
        this.event=event;
    }
    
    public void registerLeft(double v){
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description1, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description2, "pad 0 -" + v + "% 0 0");
    }
    
    public void registerRight(double v){
        v = Double.valueOf(df.format(v));
        login(false);
        layout.setComponentConstraints(title, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description1, "pad 0 -" + v + "% 0 0");
        layout.setComponentConstraints(description2, "pad 0 -" + v + "% 0 0");
    }
    
    public void loginLeft(double v){
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description1, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description2, "pad 0 " + v + "% 0 " + v + "%");

    }
    
    public void loginRight(double v){
        v = Double.valueOf(df.format(v));
        login(true);
        layout.setComponentConstraints(title, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description1, "pad 0 " + v + "% 0 " + v + "%");
        layout.setComponentConstraints(description2, "pad 0 " + v + "% 0 " + v + "%");

    }
    private void login(boolean login){
        if (this.isLogin != login){
            if (login){
                title.setText("Need to Register?");
                description1.setText("Create an Account");
                description2.setText("in just a few easy steps!");
                button.setText("Register");
            }else {
                title.setText("Welcome!");
                description1.setText("Sign in and make your voice heard!");
                description2.setText("Vote today!");
                button.setText("Sign In");
            }
            this.isLogin = login;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
