import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import ClientSocketHandler.ClientSocketHandler;

import static LoginGUI.java.main.StartupLogin.DELIMITER;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class ClientTest {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ClientSocketHandler clientSocketHandler;
    private Process serverProcess;

    @BeforeEach
    public void setUp() throws IOException, InterruptedException {
        // Execute the server jar file in a separate process
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "Server.jar");
        processBuilder.directory(new File("../out/artifacts/Server/"));
        processBuilder.redirectErrorStream(true);
        serverProcess = processBuilder.start();

        // Sleep for server to start
        Thread.sleep(1000);
        clientSocketHandler = new ClientSocketHandler("localhost", 12345);
    }

    @AfterEach
    public void tearDown() throws IOException {
        serverProcess.destroy();
        clientSocketHandler.close();
    }

    @Test
    public void testSendMessage() throws IOException {
        clientSocketHandler.sendMessage("Hello, server!");

        String response = clientSocketHandler.in.readLine();
        Assertions.assertEquals("UNKNOWN_COMMAND", response);
    }

    @Test
    public void testValidAdminLogin() throws IOException {
        clientSocketHandler.sendMessage("LOGIN\nadmin" + DELIMITER + "admin");
        String response = clientSocketHandler.in.readLine();
        Assertions.assertEquals("AUTHENTICATED_ADMIN", response);
    }

    @Test
    public void testValidUserLogin() throws IOException {
        clientSocketHandler.sendMessage("LOGIN\nuser1" + DELIMITER + "pass1");
        String response = clientSocketHandler.in.readLine();
        Assertions.assertEquals("AUTHENTICATED_USER", response);
        clientSocketHandler.sendMessage("LOGOUT");
    }

    @Test
    public void testInvalidLogin() throws IOException {
        clientSocketHandler.sendMessage("LOGIN\nuser1" + DELIMITER + "wrongpassword");
        String response = clientSocketHandler.in.readLine();
        Assertions.assertEquals("INVALID_CREDENTIALS", response);
    }

    @Test
    public void testLogout() throws IOException {
        clientSocketHandler.sendMessage("LOGIN\nuser1" + DELIMITER + "pass1");
        String response = clientSocketHandler.in.readLine();
        Assertions.assertEquals("AUTHENTICATED_USER", response);

        clientSocketHandler.sendMessage("LOGOUT");
    }

    @Test
    public void testIsRunning() {
        Assertions.assertTrue(ClientSocketHandler.isRunning());
    }

    @Test
    public void testStopRunning() {
        ClientSocketHandler.stopRunning();
        Assertions.assertFalse(ClientSocketHandler.isRunning());
    }

    @Test
    public void testRegistration() throws IOException {
        clientSocketHandler.sendMessage("REGISTER\nnewUser" + DELIMITER + "newPassword");
        String response = clientSocketHandler.in.readLine();
        Assertions.assertEquals("SUCCESS", response);

        clientSocketHandler.sendMessage("REGISTER\nnewUser" + DELIMITER + "newPassword");
        response = clientSocketHandler.in.readLine();
        Assertions.assertEquals("ALREADY_EXISTS", response);
    }

    @Test
    public void testGetSocket() throws IOException {
        Socket socket = clientSocketHandler.getSocket();
        Assertions.assertNotNull(socket);
    }
}