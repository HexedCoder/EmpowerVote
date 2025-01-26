package src.main.java.component;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
//import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import src.main.java.support.HandleData;
import support.Button;
import support.MyPasswordField;
import support.MyTextField;

import static src.main.java.support.EmpowerVoteStartup.handleLogin;
import static src.main.java.support.EmpowerVoteStartup.main;
import static src.main.java.support.HandleData.addUser;

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

    private JLabel loginStatusLabel; // Declare the JLabel to show login status
    private JLabel registrationStatusLabel; // Declare the JLabel to show registration status
    static JLabel registrationLabel;
    static JLabel loginLabel;
    static JLabel welcomeLabel;

    static ArrayList<String> createAccountStrings = new ArrayList<>(Arrays.asList("Create Account", "Crear Cuenta", "Создать аккаунт"));
    static ArrayList<String> signInStrings = new ArrayList<>(Arrays.asList("Sign In", "Iniciar Sesión", "Войти"));
    static ArrayList<String> welcomeStrings = new ArrayList<>(Arrays.asList("Welcome!", "¡Bienvenido!", "Добро пожаловать!"));

    public static void update(){
        int languageIndex = PanelCover.language.equals("Spanish") ? 1 : PanelCover.language.equals("Russian") ? 2 : 0;
        registrationLabel.setText(createAccountStrings.get(languageIndex));
        loginLabel.setText(signInStrings.get(languageIndex));
    }

    private void initRegister(){
        int languageIndex = PanelCover.language.equals("Spanish") ? 1 : PanelCover.language.equals("Russian") ? 2 : 0;
        register.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push" ));
        registrationLabel=new JLabel(createAccountStrings.get(languageIndex));
        registrationLabel.setFont(new Font("sansserif", 1, 30));
        registrationLabel.setForeground(new Color(30,95,156));
        register.add(registrationLabel);
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
        //==========================================
        //------------------------------------------
        // Add the status label to display login results
        registrationStatusLabel = new JLabel("");
        registrationStatusLabel.setFont(new Font("sansserif", 0, 14));
        registrationStatusLabel.setForeground(Color.RED); // Default to red for errors
        register.add(registrationStatusLabel, "w 60%, align center"); // Add label below the password field
        //------------------------------------------
        // Add action listener to login button
        //==========================================
        cmd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // get username and password
                String username = txtUser.getText();
                String password = new String(txtPass.getPassword());
                boolean loginStatus = addUser(username, password, 0);

                if (loginStatus) {
                    registrationStatusLabel.setText("Registration Success, please log in.");
                } else {
                    registrationStatusLabel.setText("Registration Unsuccessful, user exists!");
                }
            }
        });
    }

    private void initLogin(){
        login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push" ));
        loginLabel =new JLabel("Sign In");
        loginLabel.setFont(new Font("sansserif", 1, 30));
        loginLabel.setForeground(new Color(30,95,156));
        login.add(loginLabel);
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
        // Add the status label to display login results
        loginStatusLabel = new JLabel("");
        loginStatusLabel.setFont(new Font("sansserif", 0, 14));
        loginStatusLabel.setForeground(Color.RED); // Default to red for errors
        login.add(loginStatusLabel, "w 60%, align center"); // Add label below the password field
        //------------------------------------------
        // Add action listener to login button
        //==========================================
        cmd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // get username and password
                String username = txtUser.getText();
                String password = new String(txtPass.getPassword());
                src.main.java.support.HandleData.LoginStatus loginStatus = handleLogin(username, password);

                // Set label in GUI based on loginStatus
                switch (loginStatus) {
                    case AUTHENTICATED_USER:
                        loginStatusLabel.setText("Login successful as User!");
                        loginStatusLabel.setForeground(Color.GREEN); // Set green for success
                        break;
                    case AUTHENTICATED_ADMIN:
                        loginStatusLabel.setText("Login successful as Admin!");
                        loginStatusLabel.setForeground(Color.GREEN);
                        break;
                    case INVALID_CREDENTIALS:
                        loginStatusLabel.setText("Invalid credentials!");
                        break;
                    case ALREADY_LOGGED_IN:
                        loginStatusLabel.setText("Already logged in!");
                        break;
                    case ALREADY_VOTED:
                        loginStatusLabel.setText("Already voted!");
                        break;
                    case FAILURE:
                        loginStatusLabel.setText("Login failed, please try again.");
                        break;
                }
            }
        });
        
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
        jLabel1.setIcon(new javax.swing.ImageIcon("/images/banner.png")); // NOI18N

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

        jLabel2.setIcon(new javax.swing.ImageIcon("/images/banner.png")); // NOI18N

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
