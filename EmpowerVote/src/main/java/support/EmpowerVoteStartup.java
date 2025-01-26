package src.main.java.support;

import java.util.*;

import static src.main.java.main.Main.startup;

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

        if (serverError) {
            System.out.println("Server startup failed.");
            return;
        }

        // Start the login GUI
        startup();
    } // End of main

    public static HandleData.LoginStatus handleLogin(String username, String password) {
        return HandleData.authenticateUser(username, password);
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

        boolean result = HandleData.addUser(newUser, newPassword, UserLevel);
        if (result) {
            System.out.printf("%s registration successful.\n", isAdmin ? "Admin" : "User");
        } else {
            System.out.println("Entry already exists.");
        }
    } // End of handleRegistration

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