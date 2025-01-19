package src;

import java.util.LinkedList;
import java.util.Scanner;

public class EmpowerVoteStartup {
    public static void main(String[] args) {
        String userDataPath = "resource/UserData.tsv";
        String voteStatusPath = "resource/VoteStatus.tsv";
        boolean serverError = false;

        if (HandleData.serverStartup(userDataPath) == HandleData.StartupStatus.FAILURE) {
            System.out.println("Startup failed.");
            System.exit(1);
        }

        if (HandleData.voteStartup(voteStatusPath) == HandleData.StartupStatus.FAILURE) {
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

            String option;
            switch (loginStatus) {
                case AUTHENTICATED_USER:
                    System.out.println("User logged in.");
                    System.out.println("1: Vote\n2: Logout");

                    option = scanner.nextLine();
                    switch (option) {
                        case "1" -> {
                            LinkedList<HandleData.Candidate> candidates = HandleData.getCandidates();
                            String currentPosition = "";
                            scanner = new Scanner(System.in);

                            for (HandleData.Candidate candidate : candidates) {
                                if (!candidate.position.equals(currentPosition)) {
                                    currentPosition = candidate.position;
                                    System.out.printf("\nPosition: %s%n", currentPosition);
                                }
                                System.out.printf("\tCandidate: %s%n", candidate.name);
                            }

                            System.out.println("Enter the name of the candidate you want to vote for:");
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

                    HandleData.markUserVoted(username);
                    break;
                case AUTHENTICATED_ADMIN:
                    System.out.println("Admin logged in.\n");
                    System.out.println("1: Print Votes\n2: Register User\n3: Logout Users");
                    System.out.println("Please make a selection:");
                    option = scanner.nextLine();

                    switch (option) {
                        case "1" -> HandleData.printVotes();
                        case "2" -> {
                            System.out.println("Enter the new user's name:");
                            String newUser = scanner.nextLine();

                            System.out.println("Enter the new user's password:");
                            String newPassword = scanner.nextLine();

                            // Ensure valid username and password provided
                            if (newUser.isEmpty() || newPassword.isEmpty()) {
                                System.out.println("Invalid username or password.");
                                continue;
                            }

                            System.out.println("Enter user type (0: User 1: Admin):");
                            String userType = scanner.nextLine();

                            int userLevel;
                            if (userType.equals("0")) {
                                userLevel = 0;
                            } else if (userType.equals("1")) {
                                userLevel = 1;
                            } else {
                                System.out.println("Invalid user type.");
                                continue;
                            }

                            boolean result = HandleData.addUser(newUser, newPassword, userLevel);
                            if (result) {
                                System.out.println("User added.");
                            } else {
                                System.out.println("User Exists");
                            }
                        }
                        case "3" -> HandleData.logoutAllUsers();
                        default -> System.out.println("Invalid option.");
                    }
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
            HandleData.logoutUser(username);

        } // End of server authentication loop

        if (!serverError) {
            HandleData.serverShutdown();
        }

    } // End of main method
} // End of EmpowerVoteStartup class