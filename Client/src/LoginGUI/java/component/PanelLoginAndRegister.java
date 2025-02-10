
package LoginGUI.java.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.*;

import net.miginfocom.swing.MigLayout;
import support.Button;
import support.MyPasswordField;
import support.MyTextField;

/**
 * CMSC 495
 * @author Marc
 */
public class PanelLoginAndRegister extends javax.swing.JLayeredPane {

    private Button cmdLogin;
    private Button cmdRegister;
    private MyTextField txtLogin;
    private MyTextField txtRegister;
    private MyPasswordField txtLoginPass;
    private MyPasswordField txtRegisterPass;
    private JLabel lblRegisterMessage;


    public PanelLoginAndRegister() {
        initComponents();
        initRegister();
        initLogin();
        login.setVisible(false);
        register.setVisible(true);
    }

    private void initRegister(){
        register.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push" ));
        JLabel label=new JLabel("Create Account");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(30,95,156));
        register.add(label);
        //--------------------------------------------
        //username text field
        //============================================
        txtRegister = new MyTextField();
        txtRegister.setPrefixIcon(new ImageIcon("src/resources/user.png")); // icon for username
        txtRegister.setHint("username");
        register.add(txtRegister, "w 60%");
        //============================================
        //--------------------------------------------
        //---------------------------------------------
        //password text field
        //============================================
        txtRegisterPass = new MyPasswordField();
        txtRegisterPass.setPrefixIcon(new ImageIcon("src/resources/pass.png")); // icon for password
        txtRegisterPass.setHint("password");
        register.add(txtRegisterPass, "w 60%");
        //============================================
        //--------------------------------------------
        //---------------------------------------------
        // registration status label
        //=============================================
        // Add message label
        lblRegisterMessage = new JLabel("");
        lblRegisterMessage.setFont(new Font("sansserif", Font.BOLD, 14));
        lblRegisterMessage.setForeground(Color.RED); // Default color for errors
        register.add(lblRegisterMessage, "w 60%");
        //============================================
        //----------------------------------------------
        //------------------------------------------------
        // register button
        //================================================
        cmdRegister = new Button();
        cmdRegister.setBackground(new Color(30,95,156));
        cmdRegister.setForeground(new Color(250,250,250));
        cmdRegister.setText("Register");
        register.add(cmdRegister,"w 40%, h 40" );
        //================================================
        //------------------------------------------------
    }
    private void initLogin(){
        login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push" ));
        JLabel label=new JLabel("Sign In");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(30,95,156));
        login.add(label);
        //-----------------------------------------
        //username text field
        //=========================================
        txtLogin = new MyTextField();
        txtLogin.setPrefixIcon(new ImageIcon("src/resources/user.png")); // icon for username
        txtLogin.setHint("username");
        login.add(txtLogin, "w 60%");
        //==========================================
        //------------------------------------------
        //------------------------------------------
        //password text field
        //===========================================
        txtLoginPass = new MyPasswordField();
        txtLoginPass.setPrefixIcon(new ImageIcon("src/resources/pass.png")); // icon for password
        txtLoginPass.setHint("password");
        login.add(txtLoginPass, "w 60%");
        //===========================================
        //------------------------------------------
        //------------------------------------------
        // login button
        //==========================================
        cmdLogin = new Button();
        cmdLogin.setBackground(new Color(30,95,156));
        cmdLogin.setForeground(new Color(250,250,250));
        cmdLogin.setText("Sign In");
        login.add(cmdLogin,"w 40%, h 40" );
        //==========================================
        //------------------------------------------
        
    } // end of initLogin
    
    public void showRegister(boolean show){
        if (show) {
            login.setVisible(false);
            register.setVisible(true);
        } else {
            login.setVisible(true);
            register.setVisible(false);
        }
    }

    public void addLoginEvent(ActionListener event) {
        cmdLogin.addActionListener(event);
        System.out.println("Login event added!");
    }

    public void addRegisterEvent(ActionListener event) {
        cmdRegister.addActionListener(event);
        System.out.println("Register event added!");
    }

    public String getLoginUsername() {
        return txtLogin.getText();  // Make sure txtUser is the JTextField for the username
    }

    public String getLoginPassword() {
        return new String(txtLoginPass.getPassword());  // Ensure txtPass is the JPasswordField
    }

    public String getRegisterUsername() {
        return txtRegister.getText();  // Make sure txtUser is the JTextField for the username
    }

    public String getRegisterPassword() {
        return new String(txtRegisterPass.getPassword());  // Ensure txtPass is the JPasswordField
    }

    public void setRegisterMessage(String message, boolean isSuccess) {
        if (isSuccess) {
            lblRegisterMessage.setForeground(new Color(34, 139, 34)); // Green for success
        } else {
            lblRegisterMessage.setForeground(Color.RED); // Red for failure
        }
        lblRegisterMessage.setText(message);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        register = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("images/banner.png")); // NOI18N

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                .addContainerGap())
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(221, Short.MAX_VALUE))
        );

        add(login, "card3");

        register.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon("images/banner.png")); // NOI18N

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, registerLayout.createSequentialGroup()
                .addContainerGap(121, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(221, Short.MAX_VALUE))
        );

        add(register, "card2");
    }// </editor-fold>//GEN-END:initComponents
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel login;
    private javax.swing.JPanel register;
    // End of variables declaration//GEN-END:variables
}