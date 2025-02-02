import java.io.*;
import java.net.*;
import java.util.*;

public class EmpowerVoteServer {
    // Add volatile keyword to ensure visibility of changes to g_server_shutdown
    private static volatile boolean g_server_shutdown = false;

    public static void main(String[] args) {
        // server variables
        boolean serverError = false;

        // Initialize user variables
        String userIP = "0.0.0.0";
        int userPort = 12345;

        if (args.length == 2) {
            userIP = args[0];
            userPort = Integer.parseInt(args[1]);
        } else {
            System.err.println("Usage: main.java.EmpowerVoteServer <server ip> <server port>\n");
            System.out.println("Using default host and port: localhost:12345\n");
        }

        // Initialize to error status
        HandleData.StartupStatus startupStatus = HandleData.StartupStatus.FAILURE;

        // Try to access main.resources inside the JAR
        InputStream userDataStream = EmpowerVoteServer.class.getResourceAsStream("/UserData.tsv");

        // Set to error status if the file is not found
        if (userDataStream == null) {
            System.out.println("Failed to load user file");
            serverError = true;
        }

        // Try to access main.resources inside the JAR
        InputStream voteStatusStream = EmpowerVoteServer.class.getResourceAsStream("/VoteData.tsv");

        // Set to error status if the file is not found
        if (voteStatusStream == null) {
            System.out.println("Failed to load vote file");
            serverError = true;
        }

        // Shutdown if there was an error finding the files
        if (serverError) {
            System.out.println("Server initialization failed. Exiting...");
            return;
        }

        // Initialize users
        startupStatus = HandleData.serverStartup(userDataStream);
        if (startupStatus != HandleData.StartupStatus.SUCCESS) {
            System.out.println("Failed to load users.");
            serverError = true;
        }

        // Initialize votes
        startupStatus = HandleData.voteStartup(voteStatusStream);
        if (startupStatus != HandleData.StartupStatus.SUCCESS) {
            System.out.println("Failed to load votes.");
            serverError = true;
        }

        // Shutdown if there was an error starting
        if (serverError) {
            System.out.println("Server initialization failed. Exiting...");
            return;
        }

        // Add shutdown hook to handle Ctrl+C gracefully
        addShutdownHook();

        try (ServerSocket serverSocket = new ServerSocket(userPort, 50, InetAddress.getByName(userIP))) {
            System.out.println("Server is running and waiting for client connections on 0.0.0.0:12345...");
            System.out.println("Press CTRL+C to shutdown the server.");

            // Server loop until CTRL+C
            while (!g_server_shutdown) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                    new Thread(() -> handleClient(clientSocket)).start();
                } catch (IOException e) {
                    if (g_server_shutdown) {
                        System.out.println("Server shutting down gracefully...");
                        break;
                    }
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }

            System.out.println("Server shutdown complete.");
        } catch (IOException e) {
            System.err.println("Server encountered an error: " + e.getMessage());
        }
    } // End of main

    // Add shutdown hook to handle Ctrl+C gracefully
    private static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdown signal received. Server shutting down...");
            g_server_shutdown = true;
        }));
    } // End of addShutdownHook

    // Handle client connections
    private static void handleClient(Socket clientSocket) {
        try (
                // Initialize input and output streams
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String inputLine;

            // Read input from client
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                HandleData.LoginStatus response = processClientInput(inputLine, in, out);
                System.out.println("Response to the client: " + response);
                out.println(response);

                // Exit if the client sends "EXIT"
                if ("EXIT".equalsIgnoreCase(inputLine)) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    } // End of handleClient

    private static HandleData.LoginStatus processClientInput(String input, BufferedReader in, PrintWriter out) {
        String response = "";
        switch (input.toUpperCase()) {
            case "LOGIN":
                return handleLogin(in);
            case "REGISTER":
                return handleRegistration(in, out);
            case "VIEW_VOTES":
                if (HandleData.checkAdmin()) {
                    // Display the candidates to the client
                    response = handleViewVotes(true);

                    // Send the candidates to the client
                    out.println(response);
                    return HandleData.LoginStatus.SUCCESS;
                } else {
                    return HandleData.LoginStatus.FAILURE;
                }
            case "VOTE":
                // Display the candidates to the client
                response = handleViewVotes(false);

                // Send the candidates to the client
                out.println(response);

                // Handle the vote from the client
                return handleVote(in);

            case "LOGOUT":
                return handleLogout();
            case "EXIT":
                return HandleData.LoginStatus.SHUT_DOWN;
            default:
                return HandleData.LoginStatus.UNKNOWN_COMMAND;
        }
    } // End of processClientInput

    private static HandleData.LoginStatus handleLogin(BufferedReader in) {
        try {
            String response = in.readLine();
            String[] parts = response.split(",");

            System.out.println("Username: " + parts[0] + " Password: " + parts[1]);
            return HandleData.authenticateUser(parts[0], parts[1]);

        } catch (IOException e) {
            return HandleData.LoginStatus.FAILURE;
        }
    }

    private static HandleData.LoginStatus handleRegistration(BufferedReader in, PrintWriter out) {
        try {
            out.println("Enter new username:");
            String newUsername = in.readLine();
            out.println("Enter new password:");
            String newPassword = in.readLine();

            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                return HandleData.LoginStatus.FAILURE;
            }

            boolean result = HandleData.addUser(newUsername, newPassword, 0, new LinkedList<>());
            if (result) {
                return HandleData.LoginStatus.SUCCESS;
            } else {
                return HandleData.LoginStatus.ALREADY_EXISTS;
            }
        } catch (IOException e) {
            return HandleData.LoginStatus.FAILURE;
        }
    }

    private static String handleViewVotes(boolean isAdmin) {
        // Get the candidates from the server
        Map<String, HandleData.Candidate> candidates = HandleData.getVotes(isAdmin);

        // Return if no candidates are found
        if (candidates.isEmpty()) {
            return "No candidates found.";
        }

        // Sort the candidates by position and name for GUI
        List<HandleData.Candidate> sortedCandidates = new ArrayList<>(candidates.values());
        sortedCandidates.sort(Comparator.comparing((HandleData.Candidate c) -> c.position).thenComparing(c -> c.name));

        StringBuilder voteInfo = getStringBuilder(isAdmin, sortedCandidates);

        return voteInfo.toString();
    } // End of handleViewVotes

    private static StringBuilder getStringBuilder(boolean isAdmin, List<HandleData.Candidate> sortedCandidates) {
        StringBuilder voteInfo = new StringBuilder();
        String currentPosition = null;

        // Append the candidates to the StringBuilder (Hide votes from non-admins)
        for (HandleData.Candidate candidate : sortedCandidates) {
            if (currentPosition == null || !currentPosition.equals(candidate.position)) {
                currentPosition = candidate.position;
                voteInfo.append(String.format("Position: %s\n", candidate.position));
            }
            voteInfo.append(String.format("\tCandidate: %s%s\n", candidate.name, isAdmin ? ", Votes: " + candidate.votes : ""));
        }
        return voteInfo;
    } // End of getStringBuilder

    private static HandleData.LoginStatus handleVote(BufferedReader inputBuffer) {
        try {
            // Get the chosen candidate from the client
            String chosenCandidate = inputBuffer.readLine();

            // Return the result of voting for the chosen candidate
            return HandleData.voteForUser(chosenCandidate);
        } catch (IOException e) {
            return HandleData.LoginStatus.FAILURE;
        }
    } // End of handleVote

    // Handle logout
    private static HandleData.LoginStatus handleLogout() {
        return HandleData.logoutUser();
    }
} // End of main.java.EmpowerVoteServer