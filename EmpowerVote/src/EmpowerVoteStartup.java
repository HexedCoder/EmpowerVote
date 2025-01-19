package src;

import java.util.Scanner;

public class EmpowerVoteStartup {
    public static void main(String[] args) {
        String userDataPath = "resource/UserData.tsv";
        boolean serverError = false;

        if (HandleData.serverStartup(userDataPath) == HandleData.StartupStatus.FAILURE) {
            System.out.println("Startup failed.");
            System.exit(1);
        }

        System.out.println("Hello, EmpowerVote!");

        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter your username (or 'exit' to quit):");
            String username = scanner.nextLine();

            if (username.equals("exit")) {
                break;
            }

            System.out.println("Enter your password:");
            String password = scanner.nextLine();

            HandleData.LoginStatus loginStatus = HandleData.authenticateUser(username, password);

            switch (loginStatus) {
                case AUTHENTICATED_USER:
                    System.out.println("User logged in.");
                    HandleData.markUserVoted(username);
                    break;
                case AUTHENTICATED_ADMIN:
                    System.out.println("Admin logged in.");
                    break;
                case INVALID_CREDENTIALS:
                    System.out.println("Invalid credentials.");
                    break;
                case ALREADY_LOGGED_IN:
                    System.out.println("User already logged in.");
                    break;
                case ALREADY_VOTED:
                    System.out.println("User already voted.");
                    break;
                default:
                    System.out.println("Unknown failure.");
                    serverError = true;
                    break;
            }
        } // End of server authentication loop

        if (!serverError) {
            HandleData.serverShutdown();
        }

    } // End of main method
} // End of EmpowerVoteStartup class