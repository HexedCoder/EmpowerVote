package src;

import java.util.*;

public class EmpowerVoteStartup {
    public static void main(String[] args) {
        String userDataPath = "resource/UserData.tsv";
        String voteStatusPath = "resource/VoteStatus.tsv";
        boolean serverError = false;

        // Initialize users
        HandleData.StartupStatus startupStatus = HandleData.serverStartup(userDataPath);
        if (startupStatus != HandleData.StartupStatus.SUCCESS) {
            System.out.println("Failed to load users.");
            serverError = true;
        }

        // Initialize votes
        startupStatus = HandleData.voteStartup(voteStatusPath);
        if (startupStatus != HandleData.StartupStatus.SUCCESS) {
            System.out.println("Failed to load votes.");
            serverError = true;
        }

        // Main loop to test multiple logins
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to EmpowerVote!\n");

        boolean firstIteration = true;
        while (!serverError) {
            // Print restart on subsequent iterations
            if (!firstIteration) {
                System.out.println("Restarting to simulate other users!\n");
            }
            System.out.println("""
            Select an option:
            1: Login
            2: Register User
            3: Exit""");

            String choice = scanner.nextLine();

            // Handle user choice
            switch (choice) {
                case "1" -> {
                    handleLogin(scanner);
                    firstIteration = false;
                }
                case "2" -> {
                    handleRegistration(scanner, false);
                    firstIteration = false;
                }
                case "3" -> {
                    HandleData.serverShutdown();
                    serverError = true;
                }
                default -> System.out.println("Invalid option. Please try again.");
            } // switch

            // Set first iteration to false after first run
            firstIteration = false;
        } // while
        System.out.println("Exiting program. Goodbye!");
    } // End of main

    private static void handleLogin(Scanner scanner) {
        System.out.println("Enter your username:");
        String username = scanner.nextLine();

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        System.out.println();
        // Authenticate user with provided credentials
        HandleData.LoginStatus loginStatus = HandleData.authenticateUser(username, password);

        // Handle user actions based on login status
        switch (loginStatus) {
            case AUTHENTICATED_USER -> handleUserActions(scanner, username);
            case AUTHENTICATED_ADMIN -> handleAdminActions(scanner);
            case INVALID_CREDENTIALS -> System.out.println("\nInvalid credentials.");
            case ALREADY_LOGGED_IN -> System.out.println("\nUser already logged in.");
            case ALREADY_VOTED -> System.out.println("\nUser already voted.");
            default -> System.out.println("\nUnknown failure.");
        }
    } // End of handleLogin

    private static void handleRegistration(Scanner scanner, boolean isAdmin) {
        int UserLevel = 0; // Default to user
        System.out.printf("Enter the new %s's name: ", isAdmin ? "admin" : "user");
        String newUser = scanner.nextLine();

        System.out.printf("Enter the new %s's password: ", isAdmin ? "admin" : "user");
        String newPassword = scanner.nextLine();

        System.out.println();

        // Check if username or password is empty
        if (newUser.isEmpty() || newPassword.isEmpty()) {
            System.out.println("Invalid username or password.");
            return;
        }

        // Set user level to admin, if validated
        if (isAdmin) {
            UserLevel = 1; // Set to admin if needed
        }

        // Attempt to add user
        boolean result = HandleData.addUser(newUser, newPassword, UserLevel, new LinkedList<>());
        if (result) {
            System.out.printf("%s registration successful.\n", isAdmin ? "Admin" : "User");
        } else {
            System.out.println("Entry already exists.");
        }
    } // End of handleRegistration

    private static void handleUserActions(Scanner scanner, String username) {
        System.out.println("""
        User logged in.
        1: Vote
        2: Logout""");

        String option = scanner.nextLine();

        // Handle user actions
        switch (option) {
            case "1" -> {
                printVotes(false);

                System.out.println("Enter the name of the candidate you want to vote for: ");
                String chosenCandidate = scanner.nextLine();

                // Attempt to vote for candidate
                boolean voteResult = HandleData.voteForUser(username, chosenCandidate);
                if (voteResult) {
                    System.out.println("Vote successful.");
                } else {
                    System.out.println("Vote failed.");
                }
            }
            case "2" -> HandleData.logoutUser(username);
            default -> System.out.println("Invalid option.");
        }
    } // End of handleUserActions

    private static void printVotes(boolean isAdmin) {
        Map<String, HandleData.Candidate> candidates = HandleData.getVotes(isAdmin);

        // Collect and sort candidates by position and name in an ArrayList
        List<HandleData.Candidate> sortedCandidates = new ArrayList<>(candidates.values());
        sortedCandidates.sort(Comparator.comparing((HandleData.Candidate c) -> c.position)
                .thenComparing(c -> c.name));

        String currentPosition = "";

        // Print candidates or vote counts
        for (HandleData.Candidate candidate : sortedCandidates) {
            if (!candidate.position.equals(currentPosition)) {
                currentPosition = candidate.position;
                System.out.printf("\nPosition: %s%n", currentPosition);
            }

            if (isAdmin) {
                // Print candidate and votes
                System.out.printf("\tCandidate: %s\tVotes: %d%n", candidate.name, candidate.votes);
            } else {
                System.out.printf("\tCandidate: %s%n", candidate.name);
            }
        }
    } // End of printVotes

    private static void handleAdminActions(Scanner scanner) {
        System.out.println("""
        Admin logged in.
        1: Print Votes
        2: Register Admin
        3: Logout All Users""");

        String option = scanner.nextLine();

        // Handle admin actions
        switch (option) {
            case "1" -> printVotes(true);
            case "2" -> handleRegistration(scanner, true);
            case "3" -> HandleData.logoutAllUsers();
            default -> System.out.println("Invalid option.");
        }
    } // End of handleAdminActions
} // End of EmpowerVoteStartup