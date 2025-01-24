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

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to EmpowerVote!\n");

        while (!serverError) {
            System.out.println("""
            Select an option:
            1: Login
            2: Register User
            3: Exit""");

            String choice = scanner.nextLine();
            System.out.println();

            switch (choice) {
                case "1" -> handleLogin(scanner);
                case "2" -> handleRegistration(scanner, false);
                case "3" -> {
                    HandleData.serverShutdown();
                    serverError = true;
                }
                default -> System.out.println("Invalid option. Please try again.");
            } // switch
        } // while
        System.out.println("Exiting program. Goodbye!");
    } // End of main

    private static void handleLogin(Scanner scanner) {
        System.out.println("Enter your username:");
        String username = scanner.nextLine();

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        System.out.println();
        HandleData.LoginStatus loginStatus = HandleData.authenticateUser(username, password);

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

        if (newUser.isEmpty() || newPassword.isEmpty()) {
            System.out.println("Invalid username or password.");
            return;
        }

        if (isAdmin) {
            UserLevel = 1; // Set to admin if needed
        }

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
        switch (option) {
            case "1" -> {
                printVotes(false);

                System.out.println("Enter the name of the candidate you want to vote for: ");
                String chosenCandidate = scanner.nextLine();

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

        // Collect and sort candidates by position and name
        List<HandleData.Candidate> sortedCandidates = new ArrayList<>(candidates.values());
        sortedCandidates.sort(Comparator.comparing((HandleData.Candidate c) -> c.position)
                .thenComparing(c -> c.name));

        String currentPosition = "";

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
        switch (option) {
            case "1" -> printVotes(true);
            case "2" -> handleRegistration(scanner, true);
            case "3" -> HandleData.logoutAllUsers();
            default -> System.out.println("Invalid option.");
        }
    } // End of handleAdminActions
} // End of EmpowerVoteStartup