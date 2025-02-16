package EmpowerVoteClient;

import LoginGUI.java.main.StartupLogin;
import java.io.IOException;
import ClientSocketHandler.ClientSocketHandler;

/**
 * The EmpowerVoteClient.EmpowerVoteClient class establishes a connection to the voting server
 * and initializes the user interface.
 * <p>
 * Usage: EmpowerVoteClient.EmpowerVoteClient <server_address> <server_port>
 * If no arguments are provided, it defaults to localhost:12345.
 */
public class EmpowerVoteClient {

    private static final int DEFAULT_PORT = 12345;
    private static final String DEFAULT_ADDRESS = "localhost";

    public static void main(String[] args) {
        String serverAddress = DEFAULT_ADDRESS;
        int serverPort = DEFAULT_PORT;

        // Parse arguments if provided
        if (args.length == 2) {
            serverAddress = args[0];
            try {
                serverPort = Integer.parseInt(args[1]);
                if (serverPort < 0 || serverPort > 65535) {
                    throw new IllegalArgumentException("Invalid port number.");
                }
            } catch (IllegalArgumentException e) {
                serverPort = DEFAULT_PORT;
                System.err.println("Invalid port number. Using default port " + DEFAULT_PORT + ".");
            }
        }

        System.out.println("Connecting to server at " + serverAddress + ":" + serverPort + "...");

        // Start connection and launch GUI in a new thread
        String finalServerAddress = serverAddress;
        int finalServerPort = serverPort;

        new Thread(() -> {
            ClientSocketHandler socketHandler = null;
            try {
                socketHandler = new ClientSocketHandler(finalServerAddress, finalServerPort);
                System.out.println("Connected to the server!");

                StartupLogin login = new StartupLogin(socketHandler);
                login.setVisible(true);
                new HandleGUI(socketHandler,login);

                // Wait until the GUI is closed
                while (ClientSocketHandler.isRunning()) {
                    Thread.sleep(1000);
                }

                System.out.println("Socket closed.");
            } catch (IOException e) {
                System.err.println("Error connecting to the server: " + e.getMessage());
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
            } finally {
                // Ensure the socket handler is closed when done
                if (socketHandler != null) {
                    socketHandler.close();
                }
            }
        }).start(); // Start the thread immediately
    } // End main
} // End EmpowerVoteClient
