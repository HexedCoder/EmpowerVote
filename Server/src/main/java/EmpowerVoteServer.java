import java.io.*;
import java.net.*;
import java.util.*;

public class EmpowerVoteServer {
    // Add DELIMITER constant to separate fields in the input
    public static final String DELIMITER = ",";
    // Variables for files to parse
    public static final String USER_DATA_FILE = "resources/UserData.tsv";
    public static final String VOTE_DATA_FILE = "resources/VoteData.tsv";
    // Data structures to store user and vote data
    public static final Map<String, HandleData.User> userMap = new HashMap<>();
    public static final Map<String, HandleData.Candidate> candidateMap = new HashMap<>();
    // Define Mutex for userMap and candidateMap
    public static final Object userMapMutex = new Object();
    // Add volatile keyword to ensure visibility of changes to gServerShutdown
    private static volatile boolean gServerShutdown = false;

    public static void main(String[] args) {
        // server variables
        boolean serverError = false;
        InputStream userDataStream = null;
        InputStream voteDataStream = null;

        // Initialize user variables
        String userIP = "0.0.0.0";
        int userPort = 12345;

        if (args.length == 2) {
            userIP = args[0];
            try {
                userPort = Integer.parseInt(args[1]);
                if (userPort < 0 || userPort > 65535) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                userPort = 12345;
                System.err.println("Invalid port number. Using default port 12345.");
            }
        }

        System.out.println("Starting server on " + userIP + ":" + userPort);

        // Initialize to error status
        HandleData.StartupStatus startupStatus;

        try {
            // Try to access the USER_DATA_FILE using getDataInputStream()
            userDataStream = getDataInputStream(USER_DATA_FILE);
        } catch (IOException e) {
            // Handle the error, print the error message and set the server error status
            System.out.println("Failed to load user file: " + e.getMessage());
            serverError = true;
        }

        // Try to access the VOTE_DATA_FILE using getDataInputStream()
        try {
            voteDataStream = getDataInputStream(VOTE_DATA_FILE);
        } catch (IOException e) {
            // Handle the error, print the error message and set the server error status
            System.out.println("Failed to load vote file: " + e.getMessage());
            serverError = true;
        }

        // Shutdown if there was an error finding the files
        if (serverError) {
            System.out.println("Server initialization failed. Exiting...");
            return;
        }

        // Initialize users
        String decryptedData = "";
        try {
            decryptedData = HandleData.decryptInputStream(userDataStream);
        } catch (Exception e) {
            System.out.println("Error decrypting user data");
            serverError = true;
            decryptedData = "";
        }

        startupStatus = HandleData.serverStartup(decryptedData);
        if (startupStatus != HandleData.StartupStatus.SUCCESS) {
            System.out.println("Failed to load users.");
            serverError = true;
        }

        // Shutdown if there was an error setting up users
        if (serverError) {
            System.out.println("Server user data initialization failed. Exiting...");
            return;
        }

        // Initialize votes
        try {
            decryptedData = HandleData.decryptInputStream(voteDataStream);
        } catch (Exception e) {
            System.out.println("Error decrypting vote data");
            serverError = true;
            decryptedData = "";
        }

        startupStatus = HandleData.voteStartup(decryptedData);
        if (startupStatus != HandleData.StartupStatus.SUCCESS) {
            System.out.println("Failed to load votes.");
            serverError = true;
        }

        // Shutdown if there was an error setting up votes
        if (serverError) {
            System.out.println("Server vote data initialization failed. Exiting...");
            return;
        }

        // Add shutdown hook to handle Ctrl+C gracefully
        shutdownHook();

        try (ServerSocket serverSocket = new ServerSocket(userPort, 50, InetAddress.getByName(userIP))) {
            System.out.println("Press CTRL+C to shutdown the server.");

            // Set timeout of 1 second to check for shutdown
            serverSocket.setSoTimeout(1000);

            // Start a new thread to handle server actions
            new Thread(() -> serverActions(null)).start();

            // Server loop until CTRL+C
            while (!gServerShutdown) {
                try {
                    // Accept client connection
                    Socket clientSocket = serverSocket.accept();
                    if (gServerShutdown) {
                        if (clientSocket != null) {
                            clientSocket.close();
                        }
                        break;
                    }

                    // Print client connection
                    System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                    // Start a new thread to handle the client
                    new Thread(() -> handleClient(clientSocket)).start();
                } catch (SocketTimeoutException _) {
                    // Check for shutdown signal during Timeout
                } catch (IOException e) {
                    if (gServerShutdown) {
                        break;
                    }
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Server encountered an error: " + e.getMessage());
        }

        shutdownHook();
    } // End main

    /**
     * This method adds a shutdown hook to handle the server shutdown gracefully.
     */
    private static void shutdownHook() {
        if (!gServerShutdown) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                // Send signal to get off blocking accept() call
                gServerShutdown = true;

                System.out.println("Shutdown hook initiated.");
                HandleData.logoutAllUsers();
                HandleData.serverShutdown();
            }));
        }

    } // End shutdownHook

    /**
     * This method returns the userMap.
     *
     * @return The userMap containing user data.
     */
    public static Map<String, HandleData.User> getUserMap() {
        // synchronized block to ensure thread safety
        synchronized (userMapMutex) {
            return userMap;
        }
    } // End getUserMap

    /**
     * This method returns the candidateMap.
     *
     * @return The candidateMap containing candidate data.
     */
    public static Map<String, HandleData.Candidate> getCandidateMap() {
        // synchronized block to ensure thread safety
        synchronized (userMapMutex) {
            return candidateMap;
        }
    } // End getCandidateMap

    /**
     * This method adds a user to the userMap.
     *
     * @param newUser The user to add to the userMap.
     */
    public static void addUserToMap(HandleData.User newUser) {
        synchronized (userMapMutex) {
            userMap.put(newUser.name, newUser);
        }
    } // End addUserToMap

    /**
     * This method retrieves the InputStream from a file located in the directory of the running JAR.
     *
     * @param fileName The name of the file to access (e.g., "UserData.tsv")
     * @return InputStream for reading the file, or null if the file doesn't exist.
     * @throws IOException if an I/O error occurs while opening the file
     */
    public static InputStream getDataInputStream(String fileName) throws IOException {
        // Get the directory where the JAR is located
        String jarDirectory = System.getProperty("user.dir");

        // Construct the path to the file
        File file = new File(jarDirectory, fileName);

        // Check if file exists
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        }

        // Try to access the file as an InputStream
        return new FileInputStream(file);
    } // End getDataInputStream

    /**
     * Performs server actions based on the given command.
     */
    public static void serverActions(String command) {
        String userInput;

        // Create a scanner to read user input
        Scanner scanner = new Scanner(System.in);

        while (!gServerShutdown) {
            // Prompt the user for input
            System.out.println("Enter a command\n\t1. Log Out All Users\n\t2. Create Admin\n\t3. Shutdown");

            try {
                // Wait for user input
                userInput = scanner.nextLine();
            } catch (NoSuchElementException e) {
                // Sleep then check for shutdown signal
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _) {
                    // Ignore the exception
                }
                continue;
            }

            // Process specific commands as needed
            switch (userInput) {
                case "1":
                    System.out.println("Logging out all users...");
                    HandleData.logoutAllUsers();
                    break;
                case "2":
                    String adminUsername = "";
                    String adminPassword = "";

                    while (true) {
                        System.out.println("Enter new admin username:");
                        try {
                            adminUsername = scanner.nextLine();
                        } catch (NoSuchElementException e) {
                            break;
                        }
                        if (adminUsername.isEmpty()) {
                            System.out.println("Username cannot be empty.");
                            continue;
                        }
                        System.out.println("Enter new admin password:");
                        try {
                            adminPassword = scanner.nextLine();
                        } catch (NoSuchElementException e) {
                            break;
                        }
                        if (adminPassword.isEmpty()) {
                            System.out.println("Password cannot be empty.");
                            continue;
                        }
                        System.out.println(" Y to confirm below or N to stop.\nUsername: " + adminUsername + "\nPassword: " + adminPassword);
                        userInput = scanner.nextLine();
                        if (userInput.equalsIgnoreCase("Y") || userInput.equalsIgnoreCase("N")) {
                            break;
                        }
                    }
                    if (userInput.equalsIgnoreCase("N")) {
                        break;
                    }

                    System.out.println("Creating admin user...");
                    HandleData.addUser(adminUsername, adminPassword, 1);
                    break;
                case "3":
                    gServerShutdown = true;
                    break;
                default:
                    break;
            }
        }
    } // End serverActions

    /**
     * This method handles the client connection.
     *
     * @param clientSocket The client socket to handle.
     */
    private static void handleClient(Socket clientSocket) {
        try (
            // Initialize input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String inputLine;

            // Read input from client
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);

                // Process the client input
                HandleData.LoginStatus response = processClientInput(inputLine, in, out);

                // Print the response to the server
                if (response == HandleData.LoginStatus.LOGOUT_REQUEST) {
                    continue;
                }

                System.out.println("Response to the client: " + response);

                // Send the response to the client
                out.println(response);

                // Exit if the client sends "Exit"
                if ("EXIT".equalsIgnoreCase(inputLine)) {
                    handleLogout();
                    break;
                }

                // Exit if the client sends "Shut down"
                if ("SHUT_DOWN".equalsIgnoreCase(inputLine)) {
                    gServerShutdown = true;
                    break;
                }
            }

        } catch (SocketException e) {
            System.err.println("Client disconnected");
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    } // End handleClient

    /**
     * This method processes the client input and returns the appropriate response.
     *
     * @param input The input received from the client.
     * @param in    The BufferedReader for reading input from the client.
     * @param out   The PrintWriter for sending output to the client.
     * @return The response to the client.
     */
    private static HandleData.LoginStatus processClientInput(String input, BufferedReader in, PrintWriter out) {
        String response;
        switch (input.toUpperCase()) {
            case "LOGIN":
                return handleLogin(in);
            case "REGISTER":
                return handleRegistration(in);
            case "VOTE":
                // Display the candidates to the client
                response = handleViewVotes(false);

                if (response.isEmpty()) {
                    return HandleData.LoginStatus.FAILURE;
                }

                // Send the candidates to the client
                out.println(response);
                out.println("SUCCESS");

                // Handle the vote from the client
                return HandleData.handleVote(in);
            case "VIEW_VOTES":
                response = handleViewVotes(true);

                if (response.isEmpty()) {
                    return HandleData.LoginStatus.FAILURE;
                }

                // Send the candidates to the client
                out.println(response);
                out.println("SUCCESS");
                return HandleData.LoginStatus.SUCCESS;
            case "LOGOUT":
                handleLogout();
                return HandleData.LoginStatus.LOGOUT_REQUEST;
            case "EXIT":
                return HandleData.LoginStatus.SHUT_DOWN;
            default:
                return HandleData.LoginStatus.UNKNOWN_COMMAND;
        }
    } // End processClientInput

    /**
     * This method handles the login process.
     *
     * @param in The BufferedReader for reading input from the client.
     * @return The login status.
     */
    private static HandleData.LoginStatus handleLogin(BufferedReader in) {
        try {
            String response = in.readLine();
            String[] parts = response.split(DELIMITER);

            System.out.println("Received login credentials: " + parts[0]);
            // Check if the input is valid
            if (2 != parts.length) {
                return HandleData.LoginStatus.FAILURE;
            }

            System.out.println("Username: " + parts[0] + " Password: " + parts[1]);
            return HandleData.authenticateUser(parts[0], parts[1]);

        } catch (IOException e) {
            System.err.println("Error reading login credentials: " + e.getMessage());
            return HandleData.LoginStatus.FAILURE;
        }
    } // End handleLogin

    /**
     * This method handles the registration process.
     *
     * @param in  The BufferedReader for reading input from the client.
     * @return The registration status.
     */
    private static HandleData.LoginStatus handleRegistration(BufferedReader in) {
        try {
            String response = in.readLine();
            String[] parts = response.split(DELIMITER);

            System.out.println("Received register credentials: " + parts[0]);
            // Check if the input is valid
            if (parts.length != 2) {
                return HandleData.LoginStatus.FAILURE;
            }

            String newUsername = parts[0];
            String newPassword = parts[1];

            boolean result = HandleData.addUser(newUsername, newPassword, 0);
            if (result) {
                return HandleData.LoginStatus.SUCCESS;
            } else {
                return HandleData.LoginStatus.ALREADY_EXISTS;
            }
        } catch (IOException e) {
            return HandleData.LoginStatus.FAILURE;
        }
    } // End handleRegistration

    /**
     * This method handles the view votes process.
     *
     * @param isAdmin A boolean indicating if the user is an admin.
     * @return The vote information.
     */
    private static String handleViewVotes(boolean isAdmin) {
        // Get the candidates from the server
        Map<String, HandleData.Candidate> candidates = HandleData.getVotes(isAdmin);

        // Return if no candidates are found
        if (candidates.isEmpty()) {
            return "";
        }

        // Sort the candidates by position and name for GUI
        List<HandleData.Candidate> sortedCandidates = new ArrayList<>(candidates.values());
        sortedCandidates.sort(Comparator.comparing((HandleData.Candidate c) -> c.position).thenComparing(c -> c.name));

        StringBuilder voteInfo = getStringBuilder(isAdmin, sortedCandidates);

        return voteInfo.toString();
    } // End handleViewVotes

    /**
     * This method constructs a StringBuilder with the vote information.
     *
     * @param isAdmin          A boolean indicating if the user is an admin.
     * @param sortedCandidates A list of sorted candidates.
     * @return The StringBuilder containing the vote information.
     */
    private static StringBuilder getStringBuilder(boolean isAdmin, List<HandleData.Candidate> sortedCandidates) {
        StringBuilder voteInfo = new StringBuilder();
        String currentPosition = null;

        // Append the candidates to the StringBuilder (Hide votes from non-admins)
        for (HandleData.Candidate candidate : sortedCandidates) {
            if (currentPosition == null || !currentPosition.equals(candidate.position)) {
                if (null != currentPosition) {
                    voteInfo.append("\r\n");
                }
                voteInfo.append(String.format("%s:", candidate.position));

                currentPosition = candidate.position;
            }
            voteInfo.append(String.format("\t%s%s", candidate.name, isAdmin ? "\t" +candidate.votes : ""));
        }
        return voteInfo;
    } // End getStringBuilder

    /**
     * This method handles the vote process.
     *
     * @param chosenCandidate The candidate chosen by the user.
     * @return The vote status.
     */
    public static HandleData.LoginStatus voteForUser(String chosenCandidate) {
        // Check if the candidate exists in synchronized candidateMap
        synchronized (userMapMutex) {
            if (candidateMap.containsKey(chosenCandidate)) {
                // Increment the votes for the chosen candidate
                candidateMap.get(chosenCandidate).votes++;
                return HandleData.LoginStatus.SUCCESS;
            } else {
                return HandleData.LoginStatus.FAILURE;
            }
        }
    } // End voteForUser

    /**
     * This method handles the logout process.
     */
    private static void handleLogout() {
        HandleData.logoutUser();
    } // End handleLogout

    public static void loginUser(HandleData.User user) {
        synchronized (userMapMutex) {
            user.loggedIn = true;
        }
    }
} // End EmpowerVoteServer
