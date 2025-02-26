import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {

    private static final String USER_DATA = "Name\tPassword\tRole\tVoted\n" +
            "user1\t" + HandleData.getPasswordHash("pass1") + "\tuser\tfalse\n" +
            "adminTest\t" + HandleData.getPasswordHash("adminTest") + "\tadmin\tfalse\n";
    private static final String VOTE_DATA = """
            Name\tPosition\tVotes
            candidate1\tMayor\t0
            candidate2\tMayor\t0
            candidate3\tMayor\t0
            candidate4\tCouncil\t0
            candidate5\tCouncil\t0
            candidate6\tCouncil\t0
            candidate7\tGovernor\t0
            candidate8\tGovernor\t0
            candidate9\tGovernor\t0
            candidate10\tSenator\t0
            candidate11\tSenator\t0
            candidate12\tSenator\t0
            candidate13\tPresident\t0
            candidate14\tPresident\t0
            candidate15\tPresident\t0
            candidate16\tCongress\t0
            candidate17\tCongress\t0
            candidate18\tCongress\t0
            """;

    @BeforeEach
    public void setUp() {
        HandleData.serverStartup(USER_DATA);
        HandleData.voteStartup(VOTE_DATA);
    }

    @AfterEach
    public void tearDown() {
        EmpowerVoteServer.userMap.clear();
        EmpowerVoteServer.candidateMap.clear();
    }

    @Test
    public void testAuthenticateUser_Success() {
        HandleData.LoginStatus status = HandleData.authenticateUser("user1", "pass1");
        assertEquals(HandleData.LoginStatus.AUTHENTICATED_USER, status);
    }

    @Test
    public void testAuthenticateUser_InvalidCredentials() {
        HandleData.LoginStatus status = HandleData.authenticateUser("user1", "wrongpassword");
        assertEquals(HandleData.LoginStatus.INVALID_CREDENTIALS, status);
    }

    @Test
    public void testAuthenticateUser_AdminSuccess() {
        HandleData.LoginStatus status = HandleData.authenticateUser("adminTest", "adminTest");
        assertEquals(HandleData.LoginStatus.AUTHENTICATED_ADMIN, status);
    }

    @Test
    public void testHandleVote_Success() {
        HandleData.authenticateUser("user1", "pass1");
        InputStream inputStream = new ByteArrayInputStream("candidate1\n".getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        HandleData.LoginStatus status = HandleData.handleVote(reader);
        assertEquals(HandleData.LoginStatus.SUCCESS, status);
        assertTrue(EmpowerVoteServer.candidateMap.get("candidate1").votes > 0);
        HandleData.logoutUser();
    }

    @Test
    public void testHandleVote_InvalidCandidate() {
        HandleData.authenticateUser("user1", "pass1");
        InputStream inputStream = new ByteArrayInputStream("invalidCandidate\n".getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        HandleData.LoginStatus status = HandleData.handleVote(reader);
        assertEquals(HandleData.LoginStatus.FAILURE, status);
    }

    @Test
    public void testHandleVote_AlreadyLoggedIn() {
        HandleData.LoginStatus status = HandleData.authenticateUser("user1", "pass1");
        assertEquals(HandleData.LoginStatus.AUTHENTICATED_USER, status);

        status = HandleData.authenticateUser("user1", "pass1");
        assertEquals(HandleData.LoginStatus.ALREADY_LOGGED_IN, status);
    }

    @Test
    public void testHandleVote_AlreadyVoted() {
        HandleData.authenticateUser("user1", "pass1");
        InputStream inputStream = new ByteArrayInputStream("candidate1\n".getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        HandleData.LoginStatus status = HandleData.handleVote(reader);
        assertEquals(HandleData.LoginStatus.SUCCESS, status);
        assertTrue(EmpowerVoteServer.candidateMap.get("candidate1").votes > 0);
        assertTrue(EmpowerVoteServer.userMap.get("user1").userVoted);
    }

    @Test
    public void testAddUser_Success() {
        boolean result = HandleData.addUser("newuser", "newpassword", 0);
        assertTrue(result);
        assertNotNull(EmpowerVoteServer.userMap.get("newuser"));
    }

    @Test
    public void testAddUser_AlreadyExists() {
        boolean result = HandleData.addUser("user1", "pass1", 0);
        assertFalse(result);
    }

    @Test
    public void testLoggedinUser() {
        HandleData.authenticateUser("user1", "pass1");
        assertTrue(EmpowerVoteServer.userMap.get("user1").loggedIn);
    }

    @Test
    public void testLogoutUser() {
        HandleData.authenticateUser("user1", "pass1");
        HandleData.logoutUser();
        assertFalse(EmpowerVoteServer.userMap.get("user1").loggedIn);
    }

    @Test
    public void testGetVotes_Admin() {
        Map<String, HandleData.Candidate> candidates = HandleData.getVotes(true);
        assertEquals(VOTE_DATA.split("\n").length - 1, candidates.size());
    }

    @Test
    public void testGetVotes_NonAdmin() {
        Map<String, HandleData.Candidate> candidates = HandleData.getVotes(false);
        assertEquals(VOTE_DATA.split("\n").length - 1, candidates.size());
        for (HandleData.Candidate candidate : candidates.values()) {
            assertEquals(0, candidate.votes);
        }
    }

    @Test
    public void testRegisterUser_AlreadyExists() {
        boolean status = HandleData.addUser("user1", "password1", 0);
        assertFalse(status);
    }

    @Test
    public void testLogoutAdmin() {
        HandleData.authenticateUser("adminTest", "adminTest");
        HandleData.logoutUser();
        assertFalse(EmpowerVoteServer.userMap.get("adminTest").loggedIn);
    }

    @Test
    public void testServerStartup_InvalidUserData() {
        String invalidUserData = "Invalid Data";
        HandleData.StartupStatus status = HandleData.serverStartup(invalidUserData);
        assertEquals(HandleData.StartupStatus.FAILURE, status);
    }

    @Test
    public void testServerStartup_InvalidVoteData() {
        String invalidVoteData = "Invalid Data";
        HandleData.StartupStatus status = HandleData.voteStartup(invalidVoteData);
        assertEquals(HandleData.StartupStatus.FAILURE, status);
    }
}