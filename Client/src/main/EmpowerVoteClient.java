import java.io.*;
import java.net.*;

/**
 * The EmpowerVoteClient class establishes a connection to the voting server
 * and initializes the user interface.
 * <p>
 * Usage: EmpowerVoteClient <server_address> <server_port>
 * If no arguments are provided, it defaults to localhost:12345.
 */
public class EmpowerVoteClient {

    /**
     * The main entry point of the client application.
     * Connects to the server and starts the user interface.
     *
     * @param args Command-line arguments for server address and port (optional).
     */
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 12345;

        // Check if the user provided server address and port
        if (args.length == 2) {
            serverAddress = args[0];
            try {
                serverPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number. Using default port 12345.");
            }
        } else {
            System.err.println("Usage: EmpowerVoteClient <server_address> <server_port>");
            System.out.println("Using default server address 'localhost' and port 12345.");
        }

        // Connect to the server and start the user interface
        try (Socket socket = new Socket(serverAddress, serverPort);
             // Attempt to establish a connection to the server
             ServerConnection serverConnection = new ServerConnection(socket);

             // Create input and output streams for the socket
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            // Start new user interface thread
            UserInterface ui = new UserInterface(serverConnection, userInput);
            ui.start();

        } catch (IOException e) {
            System.err.println("Error connecting to the server: " + e.getMessage());
        }
    } // End of main
} // End of EmpowerVoteClient.java