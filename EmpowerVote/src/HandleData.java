package src;

// Import statements
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class HandleData {

    // Private fields
    private static final boolean DEBUG = false;
    private static final int USER_ATTRIBUTE_COUNT = 4;
    private static final int CANDIDATE_ATTRIBUTE_COUNT = 3;
    private static String startupUserFilename;
    private static String startupVoteFilename;

    // Maps to store user and candidate data
    private static final Map<String, User> userMap = new HashMap<>();
    private static final Map<String, Candidate> candidateMap = new HashMap<>();

    // Attempt to load user data from the specified file
    public static StartupStatus serverStartup(String filePath) {
        // Load the user data from local file
        List<User> loadedUsers = loadUserData(filePath);

        // Check if the list is empty
        if (loadedUsers.isEmpty()) {
            return StartupStatus.FAILURE;
        }

        // Add the users to the userMap
        for (User user : loadedUsers) {
            userMap.put(user.name, user);
        }

        // Store the file path for future reference
        startupUserFilename = filePath;

        return StartupStatus.SUCCESS;
    } // End of serverStartup

    public static StartupStatus voteStartup(String filePath) {
       // Load the vote data from the specified file
        List<Candidate> loadedCandidates = loadVoteData(filePath);

        // Check if the list is empty
        if (loadedCandidates.isEmpty()) {
            return StartupStatus.FAILURE;
        }

        // Add the candidates to the candidateMap
        for (Candidate candidate : loadedCandidates) {
            candidateMap.put(candidate.name, candidate);
        }

        // Store the file path for future reference
        startupVoteFilename = filePath;

        return StartupStatus.SUCCESS;
    } // End of voteStartup

    // Authenticate the user
    public static LoginStatus authenticateUser(String username, String password) {
        // Hash the provided password
        String passwordHash = getPasswordHash(password);

        // Check if the user exists and the password matches
        User user = userMap.get(username);

        // Check if the user exists
        if (null != user) {
                // Check if the password matches
                if (user.password.equals(passwordHash)) {
                    // Only allow users to login once
                    if (user.loggedIn && user.userLevel == 0) return LoginStatus.ALREADY_LOGGED_IN;

                    // Only allow users to vote once
                    if (user.userVoted) return LoginStatus.ALREADY_VOTED;

                    user.loggedIn = true;

                    // Return the user level
                    return user.userLevel == 1 ? LoginStatus.AUTHENTICATED_ADMIN : LoginStatus.AUTHENTICATED_USER;
                }
            }

        // Return if username or password error
        return LoginStatus.INVALID_CREDENTIALS;
    } // End of authenticateUser

    public static boolean voteForUser(String username, String candidateName) {
        // Check if the user and candidate exist
        User user = userMap.get(username);

        // Check if the user has already voted
        Candidate candidate = candidateMap.get(candidateName);

        // Check if the user has already voted or either user or candidate is null
        if (user == null || candidate == null || user.userVoted) return false;

        // Increment the candidate's votes and set the user as voted
        ++candidate.votes;
        user.userVoted = true;

        return true;
    } // End of voteForUser

    // Add a candidate to the candidateMap
    public static void logoutUser(String username) {
        // Check if the user exists
        User user = userMap.get(username);

        // Set the user as logged out
        if (user != null) user.loggedIn = false;
    } // End of logoutUser

    public static void logoutAllUsers() {
        // Set all users as logged out
        userMap.values().forEach(user -> user.loggedIn = false);
    } // End of logoutAllUsers

    public static boolean addUser(String username, String password, int userLevel, List<User> users) {
        // Check if the user already exists
        if (userMap.containsKey(username)) return false;

        // Hash the provided password
        String passwordHash = getPasswordHash(password);

        // Create a new user and add it to the userMap
        User newUser = new User(username, passwordHash, userLevel, false, false);

        // Add the new user to the list of users
        users.add(newUser);
        // Add the new user to the userMap
        userMap.put(username, newUser);

        return true;
    } // End of addUser

    private static String getPasswordHash(String password) {
        // Hash the provided password
        try {
            // Hash the password using SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Hash the password with a string builder
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            // Convert the hash to a hex string
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            // Return the hex string
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Throw a runtime exception if the hashing fails
            throw new RuntimeException("Error hashing password", e);
        }
    } // End of getPasswordHash

    // Shutdown the server
    public static void serverShutdown() {
        // Set all users as logged out
        userMap.values().forEach(user -> user.loggedIn = false);

        // Backup the user and vote data
        String backupUserFilename = startupUserFilename + "_bck";
        String backupVoteFilename = startupVoteFilename + "_bck";

        // Save user data
        if (saveUserData(backupUserFilename)) {
            // Replace the original file with the backup file
            if (replaceFile(backupUserFilename, startupUserFilename)) {
                System.out.println("Error updating user data file.");
            }
        } else {
            System.out.println("Error saving user data.");
        }

        // Save vote data
        if (saveVoteData(backupVoteFilename)) {
            // Replace the original file with the backup file
            if (replaceFile(backupVoteFilename, startupVoteFilename)) {
                System.out.println("Error updating vote data file.");
            }
        } else {
            System.out.println("Error saving vote data.");
        }
    } // End of serverShutdown

    private static boolean replaceFile(String backupFileName, String originalFileName) {
        File backupFile = new File(backupFileName);
        File originalFile = new File(originalFileName);

        // Delete the original file if it exists
        if (originalFile.exists() && !originalFile.delete()) {
            System.out.println("Failed to delete the original file: " + originalFileName);
            return true;
        }

        // Rename the backup file to the original file
        if (!backupFile.renameTo(originalFile)) {
            System.out.println("Failed to rename backup file: " + backupFileName);
            return true;
        }

        return false;
    } // End of replaceFile

    public static Map<String, Candidate> getVotes(boolean isAdmin) {
        // Return the candidateMap if the user is an admin
        if (isAdmin) return candidateMap;

        // Hide the votes from the user
        Map<String, Candidate> tempMap = new HashMap<>();
        for (Candidate candidate : candidateMap.values()) {
            candidate.votes = 0;
            tempMap.put(candidate.name, candidate);
        }
        return tempMap;
    } // End of getVotes

    private static int parseUserLevel(String level) {
        // Return the user level based on the input
        return switch (level) {
            case "user" -> 0;
            case "admin" -> 1;
            default -> -1;
        };
    } // End of parseUserLevel


    private static List<User> loadUserData(String filePath) {
        // Create a list to store the users
        List<User> users = new ArrayList<>();

        // Load the user data from the specified file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split("\\t");
                if (parts.length == USER_ATTRIBUTE_COUNT) {
                    try {
                        users.add(new User(parts[0], parts[1], parseUserLevel(parts[2]),
                                Boolean.parseBoolean(parts[3]), false));
                    } catch (NumberFormatException e) {
                        System.out.printf("Error parsing user data: %s%n", e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.printf("Error loading user data: %s%n", e.getMessage());
        }
        return users;
    } // End of loadUserData

    // Load the vote data from the specified file
    public static List<Candidate> loadVoteData(String filePath) {
        // Create a list to store the candidates
        List<Candidate> candidates = new ArrayList<>();

        // Load the vote data from the specified file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split("\\t");
                if (parts.length == CANDIDATE_ATTRIBUTE_COUNT) {
                    candidates.add(new Candidate(parts[0], parts[1], Integer.parseInt(parts[2])));
                }
            }
        } catch (IOException e) {
            System.out.printf("Error loading vote data: %s%n", e.getMessage());
        }
        return candidates;
    } // End of loadVoteData

    // Save the user data to the specified file
    private static boolean saveUserData(String filePath) {
        // Save the user data to the specified file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            // Write the user attribute data to the file
            writer.write("Name\tPassword\tRole\tVoted\n");

            // Write each user data to the file
            for (User user : userMap.values()) {
                writer.write(String.format("%s\t%s\t%s\t%s\n",
                        user.name, user.password, user.userLevel == 0 ? "user" : "admin", user.userVoted));
            }
        } catch (IOException e) {
            System.out.printf("Error saving user data: %s%n", e.getMessage());
            return false;
        }

        return true;
    } // End of saveUserData

    // Save the vote data to the specified file
    private static boolean saveVoteData(String filePath) {
        // Save the vote data to the specified file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the candidate attribute data to the file
            writer.write("Name\tPosition\tVotes\n");

            // Write each candidate data to the file
            for (Candidate candidate : candidateMap.values()) {
                writer.write(String.format("%s\t%s\t%d\n", candidate.name, candidate.position, candidate.votes));
            }
        } catch (IOException e) {
            System.out.printf("Error saving vote data: %s%n", e.getMessage());
            return false;
        }
        return true;
    } // End of saveVoteData

    // Enum for the login status
    public enum LoginStatus {
        AUTHENTICATED_USER, AUTHENTICATED_ADMIN, INVALID_CREDENTIALS, ALREADY_LOGGED_IN, ALREADY_VOTED, FAILURE
    } // End of LoginStatus enum

    // Enum for the startup status
    public enum StartupStatus {
        SUCCESS, FAILURE
    } // End of StartupStatus enum

    // User class
    public static class User {
        // User fields
        String name;
        String password;
        int userLevel;
        boolean userVoted;
        boolean loggedIn;

        // User constructor
        public User(String name, String password, int userLevel, boolean userVoted, boolean loggedIn) {
            this.name = name;
            this.password = password;
            if (-1 == userLevel) {
                throw new IllegalArgumentException("Invalid user level");
            }
            this.userLevel = userLevel;
            this.userVoted = userVoted;
            this.loggedIn = loggedIn;
        }
    } // End of User class

    // Candidate class
    public static class Candidate {
        // Candidate fields
        String name;
        String position;
        int votes;

        public Candidate(String name, String position, int votes) {
            this.name = name;
            this.position = position;
            this.votes = votes;
            if (DEBUG) {
                System.out.printf("New Candidate: %n\tName: %s%n\tPosition: %s%n\tVotes: %d%n",
                        name, position, votes);
            }
        } // End of Candidate constructor

    } // End of Candidate class
} // End of HandleData class