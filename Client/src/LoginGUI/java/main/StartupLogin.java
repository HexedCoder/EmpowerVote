package LoginGUI.java.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DecimalFormat;

import AdminGUI.main.StartupAdmin;
import LoginGUI.java.component.PanelCover;
import ClientSocketHandler.ClientSocketHandler;
import LoginGUI.java.component.PanelLoginAndRegister;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import javax.swing.*;

/**
 * The StartupLogin class initializes the user interface for login and registration.
 * <p>
 * Usage: StartupLogin
 *
 * CMSC 495
 * @author Marc
 */
public class StartupLogin extends javax.swing.JFrame {

    public static final String DELIMITER = ",";
    private MigLayout layout;
    private PanelCover cover;
    private PanelLoginAndRegister loginAndRegister;
    private boolean isLogin;
    private final double addSize=30;
    private final double coverSize=40;
    private final double loginSize=60;
    private final DecimalFormat df = new DecimalFormat("##0.###");
    private static BufferedReader serverIn;
    private static PrintWriter serverOut;
    private static ClientSocketHandler socketHandler;

    private javax.swing.JLayeredPane background;

    public StartupLogin(ClientSocketHandler socketHandler) throws IOException {
        StartupLogin.socketHandler = socketHandler;
        Socket socket = socketHandler.getSocket();
        serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        serverOut = new PrintWriter(socket.getOutputStream(), true);

        initComponents();
        init();
    }

    private void init(){
        layout = new MigLayout("fill, insets 0");
        cover = new PanelCover();
        loginAndRegister = new PanelLoginAndRegister();
        TimingTarget target = new TimingTargetAdapter(){

            @Override
            public void timingEvent(float fraction){
                double fractionCover;
                double fractionLogin;
                double size = coverSize;

                if(fraction<=0.5f){
                    size += fraction * size;
                }else{
                    size += addSize - fraction * addSize;
                }

                if (isLogin){
                    fractionCover = 1f - fraction;
                    fractionLogin = fraction;
                    if(fraction>=0.5f){
                        cover.registerRight(fractionCover * 100);
                    }else {
                        cover.loginRight(fractionLogin * 100);
                    }
                } else {
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                    if (fraction<=0.5f){
                        cover.registerLeft(fraction * 100);
                    }else {
                        cover.loginLeft((1f-fraction)*100);
                    }
                }
                if (fraction >= 0.5f){
                    loginAndRegister.showRegister(isLogin);
                }
                fractionCover = Double.valueOf(df.format(fractionCover));
                fractionLogin = Double.valueOf(df.format(fractionLogin));
                layout.setComponentConstraints(cover, "width " + size + "%, pos" + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(loginAndRegister, "width " + loginSize + "%, pos" + fractionLogin + "al 0 n 100%");
                background.revalidate();
            }

            @Override
            public void end(){
                isLogin = !isLogin;
            }
        };

        Animator animator = new Animator(800, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);    // For smooth animation

        background.setLayout(layout);
        background.add(cover, "width " + coverSize + "%, pos 0al 0 n 100%");
        background.add(loginAndRegister, "width " + loginSize + "%, pos 1al 0 n 100%"); // 1a1 as 100%
        cover.addEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!animator.isRunning()) {
                    animator.start();
                }
            }
        });

        // Add the login event listener properly
        loginAndRegister.addLoginEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Login button clicked!");
                PanelLoginAndRegister.LoginStatus status = performLogin();
                System.out.println("Login status: " + status);

                switch (status) {
                    case AUTHENTICATED_ADMIN -> {
                        loginAndRegister.setLoginMessage("Admin Login successful!", true);
                        loginAndRegister.disableLoginButton();

                        StartupAdmin startupAdmin = null;
                        try {
                            startupAdmin = new StartupAdmin(socketHandler);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        startupAdmin.setVisible(true);
                    }
                    case AUTHENTICATED_USER -> {
                        loginAndRegister.setLoginMessage("User Login successful!", true);
                        loginAndRegister.disableLoginButton();

                        UserGUI.java.main.StartupUser startupUser = null;
                        try {
                            startupUser = new UserGUI.java.main.StartupUser(socketHandler);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        startupUser.setVisible(true);
                    }
                    case INVALID_CREDENTIALS -> loginAndRegister.setLoginMessage("Invalid credentials!", false);
                    case ALREADY_VOTED -> loginAndRegister.setLoginMessage("You have already voted!", false);
                    case ALREADY_LOGGED_IN -> loginAndRegister.setLoginMessage("Already logged in!", false);
                    case FAILURE -> loginAndRegister.setLoginMessage("Login failed!", false);
                }
            }
        });

        // Add the register event listener properly
        loginAndRegister.addRegisterEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Register button clicked!");
                performRegister();
            }
        });
    } // End init

    /**
     * Handles the admin choices.
     */
    private void handleAdmin() {
        System.out.println("Admin Login successful!");
        String[] options = {"VIEW_VOTES", "LOGOUT_ALL", "SHUTDOWN"};

        // Get user command
        String command = (String) JOptionPane.showInputDialog(null, "Select an option", "Admin Panel", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        socketHandler.sendMessage(command);
        try {
            command = serverIn.readLine();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("Server response: " + command);

        switch (command) {
            case "VIEW_VOTES" -> {
                String line;
                try {
                    while ((line = serverIn.readLine()) != null) {
                        if (line.equals("END_OF_DATA")) {
                            break;
                        } else {
                            // Display votes
                            System.out.println(line);
                        }
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("Votes displayed.");
            }
            case "SHUTDOWN" -> {
                loginAndRegister.setLoginMessage("Server shutdown successful!", true);
            }
            default -> {
                // No action needed
            }
        }
    } // End handleAdmin

    private PanelLoginAndRegister.LoginStatus performLogin() {
        String username = loginAndRegister.getLoginUsername();
        String password = loginAndRegister.getLoginPassword();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username or password cannot be empty.");
            return PanelLoginAndRegister.LoginStatus.FAILURE;
        }

        // Ensure no DELIMITER is in username or password
        if (username.contains(DELIMITER) || password.contains(DELIMITER)) {
            System.out.println("Username or password cannot contain DELIMITER.");
            return PanelLoginAndRegister.LoginStatus.FAILURE;
        }

        System.out.println("Attempting login with username: " + username);
        System.out.println("Attempting login with password: " + password);

        // Send data to socket
        socketHandler.sendMessage("LOGIN\n" + username + DELIMITER + password);

        try {
            String response = serverIn.readLine();
            return switch (response) {
                case "AUTHENTICATED_ADMIN" -> PanelLoginAndRegister.LoginStatus.AUTHENTICATED_ADMIN;
                case "AUTHENTICATED_USER" -> PanelLoginAndRegister.LoginStatus.AUTHENTICATED_USER;
                case "INVALID_CREDENTIALS" -> PanelLoginAndRegister.LoginStatus.INVALID_CREDENTIALS;
                case "ALREADY_VOTED" -> PanelLoginAndRegister.LoginStatus.ALREADY_VOTED;
                case "ALREADY_LOGGED_IN" -> PanelLoginAndRegister.LoginStatus.ALREADY_LOGGED_IN;
                case null, default -> PanelLoginAndRegister.LoginStatus.FAILURE;
            };
        } catch (IOException e) {
            return PanelLoginAndRegister.LoginStatus.FAILURE;
        }
    } // End performLogin

    private void performRegister() {
        String username = loginAndRegister.getRegisterUsername();
        String password = loginAndRegister.getRegisterPassword();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username or password cannot be empty.");
            return;
        }

        // Ensure no DELIMITER is in username or password
        if (username.contains(DELIMITER) || password.contains(DELIMITER)) {
            System.out.println("Username or password cannot contain DELIMITER.");
            return;
        }

        System.out.println("Attempting register with " + username + ":" + password);

        // Send data to socket
        serverOut.println("REGISTER\n" + username + DELIMITER + password);

        try {
            String response = serverIn.readLine();
            System.out.println("Server response: " + response);
            switch (response) {
                case "SUCCESS" -> {
                    loginAndRegister.setRegisterMessage("Registration successful!", true);
                    loginAndRegister.disableRegisterButton();
                }
                case "ALREADY_EXISTS" -> loginAndRegister.setRegisterMessage("Username already taken!", false);
                case "FAILURE" -> loginAndRegister.setRegisterMessage("Registration failed!", false);
                case null, default -> loginAndRegister.setRegisterMessage("Unknown error occurred!", false);
            }

        } catch (IOException e) {
            System.err.println("Error receiving register response: " + e.getMessage());
        }
    } // End performRegister

    private void initComponents() {
        background = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setOpaque(true);

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 930, Short.MAX_VALUE)
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 537, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background)
        );

        pack();
        setLocationRelativeTo(null);
    }// End initComponents
} // End StartupLogin