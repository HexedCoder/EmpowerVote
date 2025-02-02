import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Manages communication between the client and the server.
 * This class provides methods to send and receive messages over a socket connection.
 */
class ServerConnection implements AutoCloseable {
    // Input and output streams for the socket connection
    private final BufferedReader in;
    private final PrintWriter out;

    /**
     * Initializes the server connection by setting up input and output streams.
     *
     * @param socket The socket connected to the server.
     * @throws IOException If an I/O error occurs while initializing the streams.
     */
    public ServerConnection(Socket socket) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Sends a message to the server.
     *
     * @param message The message to send.
     */
    public void send(String message) {
        if (message != null) {
            out.println(message);
        }
    } // End of send

    /**
     * Receives a message from the server.
     *
     * @return The message received from the server.
     * @throws IOException If an I/O error occurs while reading from the server.
     */
    public String receive() throws IOException {
        String response;

        response = in.readLine();

        if (null == response) {
            return "ERROR: No response from server";
        }
        return response;
    } // End of receive

    /**
     * Closes the input and output streams of the server connection.
     */
    @Override
    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            System.err.println("Error closing input stream: " + e.getMessage());
        }

        out.close();
    } // End of close
} // End of ServerConnection
