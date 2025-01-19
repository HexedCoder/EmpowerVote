package src;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.Objects;

public class HandleData {
    private static final boolean DEBUG = false;
    private static String startupUserFilename;
    private static String startupVoteFilename;
    // Private static list to store users
    private static LinkedList<User> users = new LinkedList<>();
    private static LinkedList<Candidate> candidates = new LinkedList<>();

    // Public method to handle server startup
    public static StartupStatus serverStartup(String filePath) {
        LinkedList<User> loadedUsers = loadAllUsers(filePath);
        if (loadedUsers.isEmpty()) {
            return StartupStatus.FAILURE;
        } else {
            users = loadedUsers;
            startupUserFilename = filePath;
            return StartupStatus.SUCCESS;
        }
    } // End of serverStartup method

    // Public method to load current votes
    public static StartupStatus voteStartup(String filePath) {
        LinkedList<Candidate> currentVotes = loadAllVotes(filePath);
        if (currentVotes.isEmpty()) {
            return StartupStatus.FAILURE;
        } else {
            candidates = currentVotes;
            startupVoteFilename = filePath;
            return StartupStatus.SUCCESS;
        }
    } // End of printVotes method

    // Public method to authenticate a user
    public static LoginStatus authenticateUser(String username, String password) {
        for (User user : users) {
            // Check if the user exists and the hashed password matches
            String passwordHash = getPasswordHash(password);

            // Check if the user exists and the password matches
            if (user.name.equals(username) && user.password.equals(passwordHash)) {
                // Check if the user is already logged in
                if (user.loggedIn) {
                    return LoginStatus.ALREADY_LOGGED_IN;
                } else if (user.userVoted) {
                    return LoginStatus.ALREADY_VOTED;
                } else {
                    // Mark the user as logged in
                    user.loggedIn = true;
                    // Return the user's role
                    if (user.userLevel == 1) {
                        return LoginStatus.AUTHENTICATED_ADMIN;
                    } else {
                        return LoginStatus.AUTHENTICATED_USER;
                    }
                }
            }
        }
        return LoginStatus.INVALID_CREDENTIALS;
    } // End of authenticateUser method

    // Public method to vote for a user
    public static boolean voteForUser(String username, String candidateName) {
        for (User user : users) {
            if (user.name.equals(username)) {
                if (user.userVoted) {
                    return false;
                }
            }
        }
        for (Candidate candidate : candidates) {
            if (candidate.name.equals(candidateName)) {
                candidate.votes++;
                for (User user : users) {
                    if (user.name.equals(username)) {
                        user.userVoted = true;
                    }
                }
                return true;
            }
        }
        return false;
    } // End of voteForUser method

    // Public method to log out a user
    public static void logoutUser(String username) {
        for (User user : users) {
            if (user.name.equals(username)) {
                user.loggedIn = false;
            }
        }
    } // End of logoutUser method

    // Public method to log out all users
    public static void logoutAllUsers() {
        for (User user : users) {
            user.loggedIn = false;
        }
    } // End of logoutAllUsers method

    // Public method to add a new user
    public static boolean addUser(String username, String password, int userLevel) {
        String passwordHash = getPasswordHash(password);
        User newUser = new User(username, passwordHash, userLevel, false, false);
        if (users.contains(newUser)) {
            return false;
        }
        users.add(newUser);

        return true;
    } // End of addUser method

    // Private method to get the password hash
    private static String getPasswordHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    } // End of getPasswordHash method

    // Public method to mark a user as voted
    public static void markUserVoted(String username) {
        for (User user : users) {
            if (user.name.equals(username)) {
                user.userVoted = true;
            }
        }
    } // End of markUserVoted method

    // Public method to handle server shutdown
    public static void serverShutdown() {
        for (User user : users) {
            user.loggedIn = false;
        }
        saveUserData(startupUserFilename + "_bck");
        saveVoteData(startupVoteFilename + "_bck");
    } // End of serverShutdown method

    // Public method to print votes
    public static void printVotes() {
        for (Candidate candidate : candidates) {
            System.out.printf("Candidate: %s%nPosition: %s%nVotes: %d%n%n",
                    candidate.name, candidate.position, candidate.votes);
        }
    } // End of printVotes method

    // Public method to print candidates
    public static LinkedList<Candidate> getCandidates() {
        return candidates;
    } // End of printCandidates method

    // Private method to load user data from a file
    private static LinkedList<User> loadUserData(String filePath) {
        users.clear();
        FileReader serverFile;
        try {
            serverFile = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            System.out.printf("Error reading file: %s%n", e.getMessage());
            return users;
        }

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(serverFile);
            String line;
            boolean firstLine = true;
            while ((line = bufferedReader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = line.split("\\t");

                if (data.length == 4) {
                    String name = data[0];
                    String password = data[1];
                    int userLevel;
                    if (Objects.equals(data[2], "user")) {
                        userLevel = 0;
                    } else if (Objects.equals(data[2], "admin")) {
                        userLevel = 1;
                    } else {
                        throw new NumberFormatException("Invalid user level: " + data[2]);
                    }
                    boolean voted = Boolean.parseBoolean(data[3]);
                    boolean loggedIn = false;
                    users.add(new User(name, password, userLevel, voted, loggedIn));
                } else {
                    throw new NumberFormatException("Invalid user data: " + line);
                }
            }
        } catch (IOException e) {
            System.out.printf("Error reading file: %s%n", e.getMessage());
        } catch (NumberFormatException e) {
            System.out.printf("Error parsing user data: %s%n", e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.printf("Error closing BufferedReader: %s%n", e.getMessage());
            }
            try {
                serverFile.close();
            } catch (IOException e) {
                System.out.printf("Error closing FileReader: %s%n", e.getMessage());
            }
        }

        return users;
    } // End of loadUserData method

    // Private method to load vote data from a file
    private static LinkedList<Candidate> loadVoteData(String filePath) {
        candidates.clear();
        FileReader serverFile;
        try {
            serverFile = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            System.out.printf("Error reading file: %s%n", e.getMessage());
            return candidates;
        }

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(serverFile);
            String line;
            boolean firstLine = true;
            while ((line = bufferedReader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] data = line.split("\\t");

                if (data.length == 3) {
                    String name = data[0];
                    String position = data[1];
                    int votes = Integer.parseInt(data[2]);
                    candidates.add(new Candidate(name, position, votes));
                } else {
                    throw new NumberFormatException("Invalid candidate data: " + line);
                }
            }
        } catch (IOException e) {
            System.out.printf("Error reading file: %s%n", e.getMessage());
        } catch (NumberFormatException e) {
            System.out.printf("Error parsing candidate data: %s%n", e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.printf("Error closing BufferedReader: %s%n", e.getMessage());
            }
            try {
                serverFile.close();
            } catch (IOException e) {
                System.out.printf("Error closing FileReader: %s%n", e.getMessage());
            }
        }

        return candidates;
    } // End of loadVoteData method

    // Private method to load all users from a file
    private static LinkedList<User> loadAllUsers(String filePath) {
        return loadUserData(filePath);
    } // End of loadAllUsers method

    // Private method to load vote data from a file
    private static LinkedList<Candidate> loadAllVotes(String filePath) {
        return loadVoteData(filePath);
    } // End of loadAllVotes method

    // Private method to save user data to a file
    private static void saveUserData(String filePath) {
        if (DEBUG) {
            System.out.printf("Saving user data to: %s%n", filePath);
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Name\tPassword\tRole\tVoted\n");
            for (User user : users) {
                writer.write(user.name + "\t" + user.password + "\t" + (user.userLevel == 0 ? "user" : "admin") + "\t"
                        + user.userVoted + "\n");
            }
        } catch (IOException e) {
            System.out.printf("Error writing file: %s%n", e.getMessage());
        }
        updateFile(filePath, startupUserFilename);
    } // End of saveUserData method

    // Private method to save vote data to a file
    private static void saveVoteData(String filePath) {
        if (DEBUG) {
            System.out.printf("Saving vote data to: %s%n", filePath);
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Name\tPosition\tVotes\n");
            for (Candidate candidate : candidates) {
                writer.write(candidate.name + "\t" + candidate.position + "\t" + candidate.votes + "\n");
            }
        } catch (IOException e) {
            System.out.printf("Error writing file: %s%n", e.getMessage());
        }
        updateFile(filePath, startupVoteFilename);
    } // End of saveVoteData method

    private static void updateFile(String sourcePath, String destinationPath) {
        try {
            // Since the backup succeeded, we can now replace the original file
            Path source = Paths.get(sourcePath);
            Path target = Paths.get(destinationPath);
            StandardCopyOption copyOption = StandardCopyOption.REPLACE_EXISTING;
            Files.move(source, target, copyOption);
        } catch (IOException e) {
            System.out.printf("Error moving file: %s%n", e.getMessage());
        }
    } // End of updateFile method

    // Public enum for login status
    public enum LoginStatus {
        AUTHENTICATED_USER,
        AUTHENTICATED_ADMIN,
        INVALID_CREDENTIALS,
        ALREADY_LOGGED_IN,
        ALREADY_VOTED,
        FAILURE
    } // End of LoginStatus enum

    // Public enum for startup status
    public enum StartupStatus {
        SUCCESS,
        FAILURE
    } // End of StartupStatus enum

    // Public nested class representing a User
    public static class User {
        String name;
        String password;
        int userLevel;
        boolean userVoted;
        boolean loggedIn;

        public User(String name, String password, int userLevel, boolean voted, boolean loggedIn) {
            this.name = name;
            this.password = password;
            this.userLevel = userLevel;
            this.userVoted = voted;
            this.loggedIn = loggedIn;
            if (DEBUG) {
                System.out.printf("New User: %n\tName: %s%n\tPassword: %s%n\tRole: %s%n\tLogged In: %b%n",
                        name, password, (userLevel == 0 ? "User" : "Admin"), loggedIn);
                if (userLevel == 0) {
                    System.out.printf("\tVoted: %b%n", voted);
                }
            }
        }
    } // End of User class

    // Public nested class representing a Candidate
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
        }
    } // End of Candidates class
} // End of HandleData class