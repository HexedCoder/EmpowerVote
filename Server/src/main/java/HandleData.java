import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * The HandleData class provides methods to handle user authentication, voting, and data management.
 */
public class HandleData {
    // Variables for user and vote data files
    private static final boolean DEBUG = false;
    private static String startupUserFilename;
    private static String startupVoteFilename;
    private static User currentUser;
    private static String aesEncryptionKey = "empower-vote-2025-encryption-key";

    /**
     * Initializes the server with user data.
     *
     * @param inputData The input data containing user data.
     * @return The status of the server startup.
     */
    public static StartupStatus serverStartup(String inputData) {
        if (null == inputData) {
            return StartupStatus.FAILURE;
        }

        List<User> loadedUsers = loadUserData(inputData);
        if (loadedUsers.isEmpty()) {
            return StartupStatus.FAILURE;
        }

        for (User user : loadedUsers) {
            EmpowerVoteServer.userMap.put(user.name, user);
        }

        startupUserFilename = EmpowerVoteServer.USER_DATA_FILE;
        return StartupStatus.SUCCESS;
    } // End serverStartup

    /**
     * Initializes the server with vote data.
     *
     * @param inputData The input data containing vote data.
     * @return The status of the vote startup.
     */
    public static StartupStatus voteStartup(String inputData) {
        if (null == inputData) {
            return StartupStatus.FAILURE;
        }

        List<Candidate> loadedCandidates = loadVoteData(inputData);
        if (loadedCandidates.isEmpty()) {
            System.out.println("No candidates loaded");
            return StartupStatus.FAILURE;
        }

        for (Candidate candidate : loadedCandidates) {
            EmpowerVoteServer.candidateMap.put(candidate.name, candidate);
        }

        startupVoteFilename = EmpowerVoteServer.VOTE_DATA_FILE;
        return StartupStatus.SUCCESS;
    } // End voteStartup

    /**
     * Authenticates a user with the provided username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The status of the user authentication.
     */
    public static LoginStatus authenticateUser(String username, String password) {
        String passwordHash = getPasswordHash(password);
        Map<String, HandleData.User> userMap = EmpowerVoteServer.getUserMap();

        User user = userMap.get(username);
        if (null != user) {
            if (user.password.equals(passwordHash)) {
                if (user.loggedIn && user.userLevel == 0) return LoginStatus.ALREADY_LOGGED_IN;
                if (user.userVoted) return LoginStatus.ALREADY_VOTED;

                EmpowerVoteServer.loginUser(user);
                currentUser = user;
                return user.userLevel == 1 ? LoginStatus.AUTHENTICATED_ADMIN : LoginStatus.AUTHENTICATED_USER;
            }
            System.out.println("Invalid password");
            return LoginStatus.INVALID_CREDENTIALS;
        } else {
            System.out.println("No user found");
        }

        return LoginStatus.INVALID_CREDENTIALS;
    } // End authenticateUser

    /**
     * Logs out the current user.
     */
    public static void logoutUser() {
        if (null != currentUser) logoutUser(currentUser.name);
    } // End logoutUser

    /**
     * Handles the vote process for a user.
     *
     * @param inputBuffer The BufferedReader to read client input.
     * @return The login status after processing the vote.
     */
    static HandleData.LoginStatus handleVote(BufferedReader inputBuffer) {
        try {
            // Get the chosen candidate from the client
            String chosenCandidate = inputBuffer.readLine();

            if (null == currentUser) return HandleData.LoginStatus.FAILURE;
            if (currentUser.userLevel == 1 || null == chosenCandidate) return HandleData.LoginStatus.FAILURE;
            if (currentUser.userVoted) return HandleData.LoginStatus.ALREADY_VOTED;

            String[] selectedCandidates = chosenCandidate.split("\t");

            HandleData.LoginStatus status = HandleData.LoginStatus.FAILURE;
            for (String candidate : selectedCandidates) {
                status = EmpowerVoteServer.voteForUser(candidate);

                if (status != HandleData.LoginStatus.SUCCESS) {
                    return status;
                }
            }

            synchronized (EmpowerVoteServer.userMapMutex) {
                if (status == HandleData.LoginStatus.SUCCESS) {
                    currentUser.userVoted = true;
                    currentUser.loggedIn = false;
                }
            }
            return status;
        } catch (IOException e) {
            return HandleData.LoginStatus.FAILURE;
        }
    } // End handleVote

    /**
     * Checks if the current user is an admin.
     *
     * @return True if the current user is an admin, false otherwise.
     */
    public static boolean checkAdmin() {
        return currentUser != null && currentUser.userLevel == 1;
    } // End checkAdmin

    /**
     * Logs out a user with the provided username.
     *
     * @param username The username of the user to log out.
     */
    public static void logoutUser(String username) {
        Map<String, HandleData.User> userMap = EmpowerVoteServer.getUserMap();

        User user = userMap.get(username);
        if (user != null) {
            user.loggedIn = false;
        }
    } // End logoutUser

    /**
     * Logs out all users.
     */
    public static void logoutAllUsers() {
        Map<String, HandleData.User> users = EmpowerVoteServer.getUserMap();

        if (users != null) {
            users.values().forEach(user -> user.loggedIn = false);
        }
    } // End logoutAllUsers

    /**
     * Adds a new user with the provided username, password, and user level.
     *
     * @param username  The username of the new user.
     * @param password  The password of the new user.
     * @param userLevel The user level of the new user.
     * @return True if the user was added successfully, false otherwise.
     */
    public static boolean addUser(String username, String password, int userLevel) {

        Map<String, HandleData.User> userMap = EmpowerVoteServer.getUserMap();
        if (userMap.containsKey(username)) return false;

        String passwordHash = getPasswordHash(password);
        User newUser = new User(username, passwordHash, userLevel, false, false);

        EmpowerVoteServer.addUserToMap(newUser);
        return true;
    } // End addUser

    /**
     * Get the password hash of the provided password.
     *
     * @param password The password to hash.
     * @return The hashed password.
     */
    static String getPasswordHash(String password) {
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
    } // End getPasswordHash

    /**
     * Shuts down the server and saves user and vote data.
     */
    public static void serverShutdown() {
        Map<String, HandleData.User> userMap = EmpowerVoteServer.getUserMap();

        if (userMap == null) return;

        userMap.values().forEach(user -> user.loggedIn = false);
        String backupUserFilename = startupUserFilename + "_bck";
        String backupVoteFilename = startupVoteFilename + "_bck";

        // Save user data
        if (saveUserData(backupUserFilename)) {
            if (replaceFile(backupUserFilename, startupUserFilename)) {
                System.out.println("Error updating user data file.");
            }
        } else {
            System.out.println("Error saving user data.");
        }

        // Save vote
        if (saveVoteData(backupVoteFilename)) {
            if (replaceFile(backupVoteFilename, startupVoteFilename)) {
                System.out.println("Error updating vote data file.");
            }
        } else {
            System.out.println("Error saving vote data.");
        }
    } // End serverShutdown

    /**
     * Replaces the original file with the backup file.
     *
     * @param backupFileName   The name of the backup file.
     * @param originalFileName The name of the original file.
     * @return True if the operation was successful, false otherwise.
     */
    private static boolean replaceFile(String backupFileName, String originalFileName) {
        File backupFile = new File(backupFileName);
        File originalFile = new File(originalFileName);

        if (originalFile.exists() && !originalFile.delete()) {
            System.out.println("Failed to delete the original file: " + originalFileName);
            return true;
        }

        if (!backupFile.renameTo(originalFile)) {
            System.out.println("Failed to rename backup file: " + backupFileName);
            return true;
        }

        return false;
    } // End replaceFile

    /**
     * Gets the list of candidates and their votes.
     *
     * @param isAdmin True if the user is an admin, false otherwise.
     * @return The list of candidates and their votes.
     */
    public static Map<String, Candidate> getVotes(boolean isAdmin) {
        Map<String, HandleData.Candidate> candidateMap = EmpowerVoteServer.getCandidateMap();
        if (isAdmin) return candidateMap;

        Map<String, Candidate> tempMap = new HashMap<>();
        for (Candidate candidate : candidateMap.values()) {
            candidate = new Candidate(candidate.name, candidate.position, 0);
            tempMap.put(candidate.name, candidate);
        }
        return tempMap;
    } // End getVotes

    /**
     * Gets the list of users.
     *
     * @return The list of users.
     */
    private static int parseUserLevel(String level) {
        return switch (level) {
            case "user" -> 0;
            case "admin" -> 1;
            default -> -1;
        };
    } // End parseUserLevel

    /**
     * Decrypts the data from the provided InputStream.
     *
     * @param inputStream The input stream to decrypt.
     * @return The decrypted String.
     * @throws Exception if an error occurs during decryption.
     */
    static String decryptInputStream(InputStream inputStream) throws Exception {
        if (null == inputStream) return null;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[256];
        int nRead;
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        inputStream.close();
        return AES256Encryption.decrypt(buffer.toString(), aesEncryptionKey);
    } // End decryptInputStream


    /**
     * Loads user data from a file.
     *
     * @param inputData The input stream containing user data.
     * @return The list of users.
     */
    private static List<User> loadUserData(String inputData) {
        List<User> users = new ArrayList<>();

        String[] lines = inputData.split("\\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            if (!line.isEmpty()) {
                String[] parts = line.split("\\t");
                if (parts.length == 4) {
                    try {
                        users.add(new User(parts[0], parts[1], parseUserLevel(parts[2]), Boolean.parseBoolean(parts[3]), false));
                    } catch (NumberFormatException e) {
                        System.out.printf("Error parsing user data: %s%n", e.getMessage());
                    }
                }
            }
        }

        return users;
    } // End loadUserData

    /**
     * Loads vote data from a file.
     *
     * @param inputData The input data containing vote data.
     * @return The list of candidates.
     */
    public static List<Candidate> loadVoteData(String inputData) {
        List<Candidate> candidates = new ArrayList<>();

        int[] candidateArray = {0, 0, 0, 0, 0, 0}; // Mayor, Council, Governor, Senator, President, Congress
        String[] lines = inputData.split("\\n");
        boolean isFirstLine = true;
        for (String line : lines) {
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }
            String[] parts = line.split("\\t");
            if (parts.length == 3) {
                candidates.add(new Candidate(parts[0], parts[1], Integer.parseInt(parts[2])));
                switch (parts[1]) {
                    case "Mayor":
                        candidateArray[0]++;
                        break;
                    case "Council":
                        candidateArray[1]++;
                        break;
                    case "Governor":
                        candidateArray[2]++;
                        break;
                    case "Senator":
                        candidateArray[3]++;
                        break;
                    case "President":
                        candidateArray[4]++;
                        break;
                    case "Congress":
                        candidateArray[5]++;
                        break;
                }
            } else {
                System.out.println(Arrays.toString(parts));
            }
        }
        // Ensure 3 candidates per position
        for (int count : candidateArray) {
            if (count != 3) {
                candidates.clear();
                System.out.println("Error loading vote data: Incorrect number of candidates");
                return candidates;
            }
        }

        return candidates;
    } // End loadVoteData

    /**
     * Saves user data to a file.
     *
     * @param filePath The path of the file to save the user data.
     * @return True if the operation was successful, false otherwise.
     */
    private static boolean saveUserData(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            StringBuilder userPlainText = new StringBuilder("Name\tPassword\tRole\tVoted\n");
            for (User user : EmpowerVoteServer.userMap.values()) {
                userPlainText.append(String.format("%s\t%s\t%s\t%s\n", user.name, user.password, user.userLevel == 0 ? "user" : "admin", user.userVoted));
            }

            String encryptedData = AES256Encryption.encrypt(String.valueOf(userPlainText), aesEncryptionKey);
            writer.write(encryptedData);
        } catch (IOException e) {
            System.out.printf("Error saving user data: %s%n", e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.printf("Error encrypting user data: %s%n", e.getMessage());
            return false;
        }
        return true;
    } // End saveUserData

    /**
     * Saves vote data to a file.
     *
     * @param filePath The path of the file to save the vote data.
     * @return True if the operation was successful, false otherwise.
     */
    private static boolean saveVoteData(String filePath) {
        Map<String, HandleData.Candidate> candidateMap = EmpowerVoteServer.getCandidateMap();
        if (null == candidateMap) {
            System.out.println("Error saving vote data: Candidate map is null");
            return false;
        }

        StringBuilder votePlainText = new StringBuilder("Name\tPosition\tVotes\n");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Candidate candidate : candidateMap.values()) {
                votePlainText.append(String.format("%s\t%s\t%d\n", candidate.name, candidate.position, candidate.votes));
            }

            String encryptedData = AES256Encryption.encrypt(String.valueOf(votePlainText), aesEncryptionKey);
            writer.write(encryptedData);
        } catch (IOException e) {
            System.out.printf("Error saving vote data: %s%n", e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.printf("Error encrypting vote data: %s%n", e.getMessage());
            return false;
        }
        return true;
    } // End saveVoteData

    /**
     * LoginStatus enum for user authentication.
     */
    public enum LoginStatus {
        AUTHENTICATED_USER, AUTHENTICATED_ADMIN, INVALID_CREDENTIALS, ALREADY_LOGGED_IN, ALREADY_EXISTS, ALREADY_VOTED, SUCCESS, FAILURE, SHUT_DOWN, LOGOUT_REQUEST, UNKNOWN_COMMAND
    } // End LoginStatus enum

    /**
     * StartupStatus enum for server startup.
     */
    public enum StartupStatus {
        SUCCESS, FAILURE
    } // End StartupStatus enum

    /**
     * User class to store user data.
     */
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
    } // End User class

    /**
     * Candidate class to store candidate data.
     */
    public static class Candidate {
        public String name;
        public String position;
        public int votes;

        public Candidate(String name, String position, int votes) {
            this.name = name;
            this.position = position;
            this.votes = votes;
            if (DEBUG) {
                System.out.printf("New Candidate: %n\tName: %s%n\tPosition: %s%n\tVotes: %d%n", name, position, votes);
            }
        } // End Candidate constructor
    } // End Candidate class
} // End HandleData class