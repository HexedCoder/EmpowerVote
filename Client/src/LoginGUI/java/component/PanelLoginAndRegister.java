package LoginGUI.java.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.*;

import LoginGUI.java.support.Button;
import LoginGUI.java.support.MyPasswordField;
import LoginGUI.java.support.MyTextField;
import net.miginfocom.swing.MigLayout;

/**
 * PanelLoginAndRegister provides the user interface for login and registration forms.
 * It includes text fields for username and password, and buttons for login and registration.
 * <p>
 * This class manages both login and registration screens, and displays appropriate messages for success or failure.
 *
 * CMSC 495
 * @author Marc
 */
public class PanelLoginAndRegister extends javax.swing.JLayeredPane {

    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel login;
    private javax.swing.JPanel register;

    private Button cmdLogin;
    private Button cmdRegister;
    private MyTextField txtLogin;
    private MyTextField txtRegister;
    private MyPasswordField txtLoginPass;
    private MyPasswordField txtRegisterPass;
    private JLabel lblRegisterMessage;
    private JLabel lblLoginMessage;

    /**
     * Constructs the PanelLoginAndRegister, initializes its components, and sets the initial visible screen.
     */
    public PanelLoginAndRegister() {
        initComponents(); // Initialize layout and components
        initRegister(); // Initialize registration form components
        initLogin();    // Initialize login form components
        login.setVisible(false); // Hide login screen initially
        register.setVisible(true); // Show register screen initially
    } // PanelLoginAndRegister

    /**
     * Initializes the registration form components including labels, text fields, and the register button.
     */
    private void initRegister() {
        register.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Create Account");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(30, 95, 156));
        register.add(label);

        // Username text field
        txtRegister = new MyTextField();
        txtRegister.setPrefixIcon(new ImageIcon("resources/user.png"));
        txtRegister.setHint("username");
        register.add(txtRegister, "w 60%");

        // Password text field
        txtRegisterPass = new MyPasswordField();
        txtRegisterPass.setPrefixIcon(new ImageIcon("resources/pass.png"));
        txtRegisterPass.setHint("password");
        register.add(txtRegisterPass, "w 60%");

        // Registration status label
        lblRegisterMessage = new JLabel("");
        lblRegisterMessage.setFont(new Font("sansserif", Font.BOLD, 14));
        lblRegisterMessage.setForeground(Color.RED);
        register.add(lblRegisterMessage, "w 60%");

        // Register button
        cmdRegister = new Button();
        cmdRegister.setBackground(new Color(30, 95, 156));
        cmdRegister.setForeground(new Color(250, 250, 250));
        cmdRegister.setText("Register");
        register.add(cmdRegister, "w 40%, h 40");
    } // initRegister

    /**
     * Initializes the login form components including labels, text fields, and the login button.
     */
    private void initLogin() {
        login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Sign In");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(30, 95, 156));
        login.add(label);

        // Username text field
        txtLogin = new MyTextField();
        txtLogin.setPrefixIcon(new ImageIcon("resources/user.png"));
        txtLogin.setHint("username");
        login.add(txtLogin, "w 60%");

        // Password text field
        txtLoginPass = new MyPasswordField();
        txtLoginPass.setPrefixIcon(new ImageIcon("resources/pass.png"));
        txtLoginPass.setHint("password");
        login.add(txtLoginPass, "w 60%");

        // Login status label
        lblLoginMessage = new JLabel("");
        lblLoginMessage.setFont(new Font("sansserif", Font.BOLD, 14));
        lblLoginMessage.setForeground(Color.RED);
        login.add(lblLoginMessage, "w 60%");

        // Login button
        cmdLogin = new Button();
        cmdLogin.setBackground(new Color(30, 95, 156));
        cmdLogin.setForeground(new Color(250, 250, 250));
        cmdLogin.setText("Sign In");
        login.add(cmdLogin, "w 40%, h 40");
    } // initLogin

    /**
     * Switches between the login and registration screens.
     *
     * @param show true to show the registration screen, false to show the login screen
     */
    public void showRegister(boolean show) {
        if (show) {
            login.setVisible(false);
            register.setVisible(true);
        } else {
            login.setVisible(true);
            register.setVisible(false);
        }
    } // showRegister

    /**
     * Adds the login event listener to the login button.
     *
     * @param event the action listener to be added to the login button
     */
    public void addLoginEvent(ActionListener event) {
        cmdLogin.addActionListener(event);
    } // addLoginEvent

    /**
     * Adds the register event listener to the register button.
     *
     * @param event the action listener to be added to the register button
     */
    public void addRegisterEvent(ActionListener event) {
        cmdRegister.addActionListener(event);
    } // addRegisterEvent

    /**
     * Retrieves the username entered in the login form.
     *
     * @return the username entered in the login form
     */
    public String getLoginUsername() {
        return txtLogin.getText();
    } // getLoginUsername

    /**
     * Retrieves the password entered in the login form.
     *
     * @return the password entered in the login form
     */
    public String getLoginPassword() {
        return new String(txtLoginPass.getPassword());
    } // getLoginPassword

    /**
     * Retrieves the username entered in the registration form.
     *
     * @return the username entered in the registration form
     */
    public String getRegisterUsername() {
        return txtRegister.getText();
    } // getRegisterUsername

    /**
     * Retrieves the password entered in the registration form.
     *
     * @return the password entered in the registration form
     */
    public String getRegisterPassword() {
        return new String(txtRegisterPass.getPassword());
    } // getRegisterPassword

    /**
     * Sets the message displayed in the registration status label.
     *
     * @param message the message to display
     * @param isSuccess true if the registration is successful, false if it failed
     */
    public void setRegisterMessage(String message, boolean isSuccess) {
        if (isSuccess) {
            lblRegisterMessage.setForeground(new Color(34, 139, 34)); // Green for success
        } else {
            lblRegisterMessage.setForeground(Color.RED); // Red for failure
        }
        lblRegisterMessage.setText(message);
    } // setRegisterMessage

    /**
     * Sets the message displayed in the login status label.
     *
     * @param message the message to display
     * @param isSuccess true if the login is successful, false if it failed
     */
    public void setLoginMessage(String message, boolean isSuccess) {
        if (isSuccess) {
            lblLoginMessage.setForeground(new Color(34, 139, 34)); // Green for success
        } else {
            lblLoginMessage.setForeground(Color.RED); // Red for failure
        }
        lblLoginMessage.setText(message);
    } // setLoginMessage

    /**
     * Disables the register button.
     */
    public void disableRegisterButton() {
        cmdRegister.setEnabled(false);
    } // disableRegisterButton

    /**
     * Disables the login button.
     */
    public void disableLoginButton() {
        cmdLogin.setEnabled(false);
    } // disableLoginButton

    /**
     * Closes the GUI window.
     */
    public void closeGUI() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.dispose();
        }
    } // closeGUI

    @SuppressWarnings("unchecked")
    private void initComponents() {

        login = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        register = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("resources/banner.png"));

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

        jLabel2.setIcon(new javax.swing.ImageIcon("resources/banner.png")); // NOI18N

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
    }// End of initComponents

    /**
     * LoginStatus enum for user authentication.
     */
    public enum LoginStatus {
        AUTHENTICATED_USER, AUTHENTICATED_ADMIN, INVALID_CREDENTIALS, ALREADY_LOGGED_IN, ALREADY_EXISTS, ALREADY_VOTED, SUCCESS, FAILURE, SHUT_DOWN, LOGOUT_REQUEST, UNKNOWN_COMMAND
    } // End of LoginStatus enum
} // PanelLoginAndRegister