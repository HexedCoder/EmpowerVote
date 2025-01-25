
package component;

import java.awt.Color;
import java.awt.Font;
//import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import support.Button;
import support.MyPasswordField;
import support.MyTextField;

/**
 * CMSC 495
 * @author Marc
 */
public class PanelLoginAndRegister extends javax.swing.JLayeredPane {

  
    
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
        MyTextField txtUser = new MyTextField();
        //txtUser.setPrefixIcon(new ImageIcon(getClass().getResource("/resources/user.png"))); // icon for username - path issue
        txtUser.setHint("username");
        register.add(txtUser, "w 60%");
        //============================================
        //--------------------------------------------
        //---------------------------------------------
        //password text field
        //============================================
        MyPasswordField txtPass = new MyPasswordField();
        //txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/resources/pass.png"))); // icon for password - path issue
        txtPass.setHint("password");
        register.add(txtPass, "w 60%");
        //============================================
        //----------------------------------------------
        //------------------------------------------------
        // register button
        //================================================
        Button cmd = new Button();
        cmd.setBackground(new Color(30,95,156));
        cmd.setForeground(new Color(250,250,250));
        cmd.setText("Register");
        register.add(cmd,"w 40%, h 40" );
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
        MyTextField txtUser = new MyTextField();
        //txtUser.setPrefixIcon(new ImageIcon(getClass().getResource("/src/main/resources/images/user.png"))); // icon for username - path issue
        txtUser.setHint("username");
        login.add(txtUser, "w 60%");
        //==========================================
        //------------------------------------------
        //------------------------------------------
        //password text field
        //===========================================
        MyPasswordField txtPass = new MyPasswordField();
        //txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/src/main/resources/images/pass.png"))); // icon for password - path issue
        txtPass.setHint("password");
        login.add(txtPass, "w 60%");
        //===========================================
        //------------------------------------------
        //------------------------------------------
        // login button
        //==========================================
        Button cmd = new Button();
        cmd.setBackground(new Color(30,95,156));
        cmd.setForeground(new Color(250,250,250));
        cmd.setText("Sign In");
        login.add(cmd,"w 40%, h 40" );
        //==========================================
        //------------------------------------------
        
    }
    
    public void showRegister(boolean show){
        if (show) {
            login.setVisible(false);
            register.setVisible(true);
        } else {
            login.setVisible(true);
            register.setVisible(false);
        }
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
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/banner.png"))); // NOI18N

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

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/banner.png"))); // NOI18N

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
