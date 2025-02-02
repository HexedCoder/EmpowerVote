import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * The UserInterface class provides a command-line interface for the EmpowerVoteClient.
 * It handles user input, sends commands to the server, and displays responses.
 */
class UserInterface {
    public static final String DELIMITER = ",";
    // The server private variables for the UserInterface class
    private static volatile boolean clientActive = true;
    private final ServerConnection server;
    private final BufferedReader userInput;
    private final List<String> validCommands = Arrays.asList("LOGIN", "REGISTER", "EXIT");

    /**
     * Initializes the user interface with a server connection and input reader.
     *
     * @param server    The server connection to communicate with.
     * @param userInput The input reader to read user commands.
     */
    public UserInterface(ServerConnection server, BufferedReader userInput) {
        this.server = server;
        this.userInput = userInput;
    } // End of UserInterface

    /**
     * Starts the user interface by displaying a prompt for user commands.
     * The user can log in, register, or exit the application.
     *
     * @throws IOException If an I/O error occurs while reading user input.
     */
    public void start() throws IOException {
        while (clientActive) {
            System.out.println("\nEnter command (Login, Register, Exit): ");

            // Match formatting of valid commands
            String command = userInput.readLine().toUpperCase();

            // Check if the command is valid
            if (!validCommands.contains(command)) {
                System.out.println("Invalid command. Try again.");
                continue;
            }

            // Exit the application upon user request
            if ("EXIT".equals(command)) {
                System.out.println("Exiting...");
                break;
            }

            // Process the user command
            processCommand(command);
        } // End of while
        System.out.println("Shutting down server and client.");
    } // End of start

    private void processCommand(String command) throws IOException {
        // Handle user commands
        switch (command) {
            case "LOGIN":
                handleLogin();
                break;
            case "REGISTER":
                handleRegister();
                break;
        }
    } // End of processCommand

    private void handleLogin() throws IOException {
        String response;

        System.out.print("Enter username: ");
        String username = userInput.readLine();

        System.out.print("Enter password: ");
        String password = userInput.readLine();

        // Validate username and password
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Invalid username or password format.");
            return;
        }

        // Ensure username and password do not contain input delimiter
        if (username.contains(DELIMITER) || password.contains(DELIMITER)) {
            System.out.printf("Username or password cannot contain '%s'.\n", DELIMITER);
            return;
        }

        server.send("LOGIN");
        server.send(username + DELIMITER + password);

        // Allow time for the server to respond with a safe sleep
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            System.err.println("Error sleeping thread.");
        }

        response = server.receive();
        if (response == null) {
            System.out.println("Connection closed by server.");
            return;
        }

        handleLoginResponse(response);
    } // End of handleLogin

    /**
     * Handles the server response to a login request.
     *
     * @param response The server response to the login request.
     * @throws IOException If an I/O error occurs while reading user input.
     */
    private void handleLoginResponse(String response) throws IOException {
        // Handle login response
        try {
            // Convert the server response to a LoginStatus enum
            LoginStatus status = LoginStatus.valueOf(response);

            // Process the login status
            switch (status) {
                case AUTHENTICATED_USER:
                    showUserMenu();
                    break;
                case AUTHENTICATED_ADMIN:
                    showAdminMenu();
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
                default:
                    System.out.println("Login failed due to an unknown error.");
                    // Clear buffer
                    server.receive();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown response from server: " + response);
        }
    } // End of handleLoginResponse

    /**
     * Handles the registration process by sending a username and password to the server.
     *
     * @throws IOException If an I/O error occurs while reading user input.
     */
    private void handleRegister() throws IOException {
        System.out.print("Enter new username: ");
        String username = userInput.readLine().trim();

        System.out.print("Enter new password: ");
        String password = userInput.readLine().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Invalid username or password format.");
            return;
        }

        if (username.contains(DELIMITER) || password.contains(DELIMITER)) {
            System.out.printf("Username or password cannot contain '%s'.\n", DELIMITER);
            return;
        }

        server.send("REGISTER");
        server.send(username + DELIMITER + password);

        String response = server.receive();

        switch (response) {
            case "SUCCESS":
                System.out.println("Registration successful.");
                break;
            case "ALREADY_EXISTS":
                System.out.println("Username already exists.");
                break;
            default:
                System.out.println("Registration failed.");
        }
    } // End of handleRegister

    /**
     * Displays the admin menu with options to view votes or log out.
     *
     * @throws IOException If an I/O error occurs while reading user input.
     */
    private void showAdminMenu() throws IOException {
        while (true) {
            System.out.println("\nAdmin Menu:\n1: View Votes\n2: Logout\n3: Logout All\n4. Shutdown\nEnter option: ");

            // Match formatting of valid commands
            String choice = userInput.readLine().trim();

            switch (choice) {
                case "1":
                    server.send("VIEW_VOTES");
                    receiveAndPrintResponses();
                    break;
                case "2":
                    server.send("LOGOUT");
                    return;
                case "3":
                    server.send("LOGOUT_ALL");
                    return;
                case "4":
                    server.send("SHUTDOWN");
                    clientActive = false;
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } // End of while
    } // End of showAdminMenu

    /**
     * Displays the user menu with options to vote or log out.
     *
     * @throws IOException If an I/O error occurs while reading user input.
     */
    private void showUserMenu() throws IOException {
        while (true) {
            System.out.println("\nUser Menu:\n1: Vote\n2: Logout\nEnter option: ");
            String inputBuffer = userInput.readLine().trim();

            switch (inputBuffer) {
                case "1":
                    server.send("VOTE");
                    receiveAndPrintResponses();

                    System.out.print("Enter candidate name to vote for: ");
                    String candidate = userInput.readLine().trim();

                    if (candidate.isEmpty()) {
                        System.out.println("Invalid candidate name.");
                        break;
                    }
                    server.send(candidate);

                    // Get response
                    inputBuffer = server.receive();

                    if (inputBuffer.equals("SUCCESS")) {
                        server.send("LOGOUT");
                        System.out.println("Vote cast successfully.");
                        return;
                    } else {
                        System.out.println("Failed to cast vote.");
                    }
                    break;
                case "2":
                    // Logout the user
                    server.send("LOGOUT");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } // End of while
    } // End of showUserMenu

    private void receiveAndPrintResponses() throws IOException {
        String serverResponse;
        while ((serverResponse = server.receive()) != null && !serverResponse.contains("SUCCESS")) {
            System.out.println(serverResponse);
        }
    } // End of receiveAndPrintResponses

    /**
     * The LoginStatus enum represents the possible login statuses returned by the server.
     */
    enum LoginStatus {
        AUTHENTICATED_USER, AUTHENTICATED_ADMIN, INVALID_CREDENTIALS, ALREADY_LOGGED_IN, ALREADY_EXISTS, ALREADY_VOTED, SUCCESS, FAILURE, SHUT_DOWN, UNKNOWN_COMMAND
    } // End of LoginStatus

} // End of UserInterface