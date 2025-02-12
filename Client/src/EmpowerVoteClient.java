import LoginGUI.java.main.StartupLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The EmpowerVoteClient class establishes a connection to the voting server
 * and initializes the user interface.
 * <p>
 * Usage: EmpowerVoteClient <server_address> <server_port>
 * If no arguments are provided, it defaults to localhost:12345.
 */
public class EmpowerVoteClient {

    private static volatile boolean clientActive = true;

    /**
     * The main entry point of the client application.
     * Connects to the server and starts the user interface.
     *
     * @param args Command-line arguments for server address and port (optional).
     */
    public static void main(String[] args) {
        String serverAddress;
        int serverPort;

        // Check if the user provided server address and port
        if (args.length == 2) {
            serverAddress = args[0];
            try {
                serverPort = Integer.parseInt(args[1]);
                if (serverPort < 0 || serverPort > 65535) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                serverPort = 12345;
                System.err.println("Invalid port number. Using default port 12345.");
            }
        } else {
            serverAddress = "localhost";
            serverPort = 12345;
        }

        System.out.println("Connecting to server at " + serverAddress + ":" + serverPort + "...");

        // Connect to the server and start the user interface
        int finalServerPort = serverPort;

        new Thread(() -> {
            try (Socket socket = new Socket(serverAddress, finalServerPort)) {
                System.out.println("Connected to the server!");

                // Create input/output streams for the socket
                BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);

                // Start the login GUI
                StartupLogin login = new StartupLogin(socket);
                login.setVisible(true);

                // Wait for the client to be active before handling socket communication
                while (clientActive) {
                    try {
                        // Sleep for a while to prevent CPU overuse
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        System.err.println("Error sleeping thread: " + e.getMessage());
                    }
                }

                try {
                    socket.close();
                    System.out.println("Socket closed.");
                } catch (IOException e) {
                    System.err.println("Error closing socket: " + e.getMessage());
                }
            } catch (IOException e) {
                System.err.println("Error connecting to the server: " + e.getMessage());
            }
        }).start(); // Start the thread immediately
    } // End of main
} // End of EmpowerVoteClient.java