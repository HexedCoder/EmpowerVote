import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class EmpowerVoteClient {
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

        // Connect to the server
        try (Socket socket = new Socket(serverAddress, serverPort);
             // Create input and output streams for the socket
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

             // Create input stream for user input
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            // Array of valid commands
            ArrayList<String> commands = new ArrayList<>(Arrays.asList("LOGIN", "REGISTER", "EXIT"));

            String userCommand = "";
            while (true) {
                System.out.println("\nEnter command (Login, Register, Exit): ");
                userCommand = userInput.readLine();

                // Check if the command is valid
                if (!commands.contains(userCommand.toUpperCase())) {
                    System.out.println("Invalid command. Try again.");
                    continue;
                }

                // Send the command to the server
                out.println(userCommand);

                // Process the command
                if ("LOGIN".equalsIgnoreCase(userCommand)) {
                    // Prompt for login details
                    System.out.print("Enter username: ");
                    String username = userInput.readLine();

                    System.out.print("Enter password: ");
                    String password = userInput.readLine();

                    if (username.isEmpty() || password.isEmpty()) {
                        System.out.println("Username and password cannot be empty.");
                        continue;
                    } else if (username.contains(",") || password.contains(",")) {
                        System.out.println("Username and password cannot contain commas.");
                        continue;
                    }

                    // Send password to server
                    out.println(username + "," + password);

                    // Read login status from the server
                    String loginResponse = in.readLine();
                    if (loginResponse == null) {
                        System.out.println("Connection closed by server.");
                        break;
                    }

                    // Process the login status
                    LoginStatus loginStatus = LoginStatus.FAILURE;
                    try {
                        loginStatus = LoginStatus.valueOf(loginResponse);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Unknown response from server: " + loginResponse);
                    }

                    // Show the appropriate menu based on the login status
                    switch (loginStatus) {
                        case AUTHENTICATED_USER:
                            showUserMenu(in, out, username);
                            break;

                        case AUTHENTICATED_ADMIN:
                            showAdminMenu(in, out);
                            break;

                        case INVALID_CREDENTIALS:
                            System.out.println("Invalid username or password.");
                            break;

                        case ALREADY_LOGGED_IN:
                            System.out.println("You are already logged in.");
                            break;

                        case ALREADY_VOTED:
                            System.out.println("You have already voted.");
                            break;

                        // Handle other login statuses
                        case FAILURE:
                        default:
                            System.out.println("Login failed due to an unknown error.");
                            break;
                    }

                } else if ("REGISTER".equalsIgnoreCase(userCommand)) {
                    // Prompt for registration details
                    System.out.print("Enter new username: ");
                    String newUsername = userInput.readLine();

                    System.out.print("Enter new password: ");
                    String newPassword = userInput.readLine();
                    out.println(newUsername + newPassword);

                    // Get the registration result
                    LoginStatus registrationStatus = LoginStatus.valueOf(in.readLine());
                    if (registrationStatus == LoginStatus.SUCCESS) {
                        System.out.println("Server: Registration successful.");
                    } else {
                        System.out.println("Server: Registration failed.");
                    }
                }

                // Check if the user wants to exit
                if ("EXIT".equalsIgnoreCase(userCommand)) {
                    System.out.println("Exiting...");
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error connecting to the server: " + e.getMessage());
        }
    } // End of main method

    // Admin menu after login
    private static void showAdminMenu(BufferedReader in, PrintWriter out) throws IOException {
        String serverResponse;
        while (true) {
            System.out.println("\nAdmin Menu:\n1: View Votes\n2: Logout\nEnter option: ");
            String choice = new BufferedReader(new InputStreamReader(System.in)).readLine();

            switch (choice) {
                case "1":
                    out.println("VIEW_VOTES");  // Send command to server to view votes
                    do {
                        serverResponse = in.readLine();
                        System.out.println(serverResponse);
                    } while (!serverResponse.equals("SUCCESS"));
                    break;
                case "2":
                    out.println("LOGOUT");  // Send logout request to server
                    serverResponse = in.readLine();
                    System.out.println("Server: " + serverResponse);
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    } // End of showAdminMenu method

    // User menu after login
    private static void showUserMenu(BufferedReader in, PrintWriter out, String username) throws IOException {
        String serverResponse;
        while (true) {
            System.out.println("\nUser Menu:\n1: Vote\n2: Logout\nEnter option: ");
            String choice = new BufferedReader(new InputStreamReader(System.in)).readLine();

            switch (choice) {
                case "1":
                    out.println("VOTE");  // Send vote command to the server
                    while ((serverResponse = in.readLine()) != null && !serverResponse.isEmpty()) {
                        System.out.println(serverResponse);
                    }
                    System.out.print(serverResponse + "Enter candidate name to vote for: ");
                    String candidate = new BufferedReader(new InputStreamReader(System.in)).readLine();
                    out.println(candidate);  // Send the candidate's name to the server

                    // Get the server's response
                    serverResponse = in.readLine();
                    System.out.println("Server: " + serverResponse);  // Server responds with vote status
                    break;
                case "2":
                    out.println("LOGOUT");  // Send logout request to the server

                    // Get the server's response
                    serverResponse = in.readLine();
                    System.out.println("Server: " + serverResponse);  // Server sends logout status
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    } // End of showUserMenu method

    // Enum for login status
    public enum LoginStatus {
        AUTHENTICATED_USER, AUTHENTICATED_ADMIN, INVALID_CREDENTIALS, ALREADY_LOGGED_IN, ALREADY_EXISTS, ALREADY_VOTED, SUCCESS, FAILURE, SHUT_DOWN, UNKNOWN_COMMAND
    } // End of LoginStatus enum
} // End of EmpowerVoteClient class