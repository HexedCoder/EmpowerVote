import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

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

    @BeforeEach
    public void setUp() throws IOException {
        clientSocketHandler = new ClientSocketHandler("localhost", 12345);
    }

    @AfterEach
    public void tearDown() throws IOException {
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
    public void testGetSocket() throws IOException {
        Socket socket = clientSocketHandler.getSocket();
        Assertions.assertNotNull(socket);
    }
}