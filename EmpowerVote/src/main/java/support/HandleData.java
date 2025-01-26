package src.main.java.support;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class HandleData {
    public enum LoginStatus {
        AUTHENTICATED_USER, AUTHENTICATED_ADMIN, INVALID_CREDENTIALS, ALREADY_LOGGED_IN, ALREADY_VOTED, FAILURE
    } // End of LoginStatus enum

    private static final boolean DEBUG = false;
    private static String startupUserFilename;
    private static String startupVoteFilename;

    private static final Map<String, User> userMap = new HashMap<>();
    private static final Map<String, Candidate> candidateMap = new HashMap<>();

    public static StartupStatus serverStartup(String filePath) {
        List<User> loadedUsers = loadUserData(filePath);
        if (loadedUsers.isEmpty()) {
            return StartupStatus.FAILURE;
        }

        for (User user : loadedUsers) {
            userMap.put(user.name, user);
        }

        startupUserFilename = filePath;
        return StartupStatus.SUCCESS;
    } // End of serverStartup

    public static StartupStatus voteStartup(String filePath) {
        List<Candidate> loadedCandidates = loadVoteData(filePath);
        if (loadedCandidates.isEmpty()) {
            return StartupStatus.FAILURE;
        }

        for (Candidate candidate : loadedCandidates) {
            candidateMap.put(candidate.name, candidate);
        }

        startupVoteFilename = filePath;
        return StartupStatus.SUCCESS;
    } // End of voteStartup

    public static LoginStatus authenticateUser(String username, String password) {
        String passwordHash = getPasswordHash(password);

        User user = userMap.get(username);
        if (null != user) {
                if (user.password.equals(passwordHash)) {
                    if (user.loggedIn && user.userLevel == 0) return LoginStatus.ALREADY_LOGGED_IN;
                    if (user.userVoted) return LoginStatus.ALREADY_VOTED;

                    user.loggedIn = true;
                    return user.userLevel == 1 ? LoginStatus.AUTHENTICATED_ADMIN : LoginStatus.AUTHENTICATED_USER;
                }
            }

        return LoginStatus.INVALID_CREDENTIALS;
    } // End of authenticateUser

    public static boolean voteForUser(String username, String candidateName) {
        User user = userMap.get(username);
        Candidate candidate = candidateMap.get(candidateName);

        if (user == null || candidate == null || user.userVoted) return false;

        ++candidate.votes;
        user.userVoted = true;
        return true;
    } // End of voteForUser

    public static void logoutUser(String username) {
        User user = userMap.get(username);
        if (user != null) user.loggedIn = false;
    } // End of logoutUser

    public static void logoutAllUsers() {
        userMap.values().forEach(user -> user.loggedIn = false);
    } // End of logoutAllUsers

    public static boolean addUser(String username, String password, int userLevel) {
        if (userMap.containsKey(username)) return false;

        String passwordHash = getPasswordHash(password);
        User newUser = new User(username, passwordHash, userLevel, false, false);

        userMap.put(username, newUser);
        return true;
    } // End of addUser

    private static String getPasswordHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    } // End of getPasswordHash

    public static void serverShutdown() {
        userMap.values().forEach(user -> user.loggedIn = false);
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
        return switch (level) {
            case "user" -> 0;
            case "admin" -> 1;
            default -> -1;
        };
    } // End of parseUserLevel


    private static List<User> loadUserData(String filePath) {
        List<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split("\t");
                if (parts.length == 4) {
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

    public static List<Candidate> loadVoteData(String filePath) {
        List<Candidate> candidates = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split("\t");
                if (parts.length == 3) {
                    candidates.add(new Candidate(parts[0], parts[1], Integer.parseInt(parts[2])));
                }
            }
        } catch (IOException e) {
            System.out.printf("Error loading vote data: %s%n", e.getMessage());
        }
        return candidates;
    } // End of loadVoteData

    private static boolean saveUserData(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Name\tPassword\tRole\tVoted\n");
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

    private static boolean saveVoteData(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Name\tPosition\tVotes\n");
            for (Candidate candidate : candidateMap.values()) {
                writer.write(String.format("%s\t%s\t%d\n", candidate.name, candidate.position, candidate.votes));
            }
        } catch (IOException e) {
            System.out.printf("Error saving vote data: %s%n", e.getMessage());
            return false;
        }
        return true;
    } // End of saveVoteData

    public enum StartupStatus {
        SUCCESS, FAILURE
    } // End of StartupStatus enum

    public static class User {
        String name;
        String password;
        int userLevel;
        boolean userVoted;
        boolean loggedIn;

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

    public static class Candidate {
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