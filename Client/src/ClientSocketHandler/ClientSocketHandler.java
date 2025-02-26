package ClientSocketHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientSocketHandler encapsulates the socket connection and its I/O streams.
 * It provides methods for sending messages to the server and listening for incoming messages.
 */
public class ClientSocketHandler {

    // private variables
    private final Socket socket;
    private Thread listenerThread;
    private static volatile boolean running;

    // public variables
    public final BufferedReader in;
    public final PrintWriter out;

    /**
     * Constructs a ClientSocketHandler and connects to the specified server.
     *
     * @param serverAddress the server address
     * @param serverPort    the server port
     * @throws IOException if an I/O error occurs when creating the socket
     */
    public ClientSocketHandler(String serverAddress, int serverPort) throws IOException {
        socket = new Socket(serverAddress, serverPort);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        running = true;
    } // ClientSocketHandler

    /**
     * Returns whether the client is currently running.
     * @return true if the client is running, false otherwise
     */
    public static boolean isRunning() {
        return running;
    }

    /**
     * Stops the client from running.
     */
    public static void stopRunning() {
        ClientSocketHandler.running = false;
    } // stopRunning

    /**
     * Starts the listener thread to handle incoming messages from the server.
     */
    private void startListener() {
        listenerThread = new Thread(() -> {
            try {
                String message;
                while (running && (message = in.readLine()) != null) {
                    handleMessage(message);
                }
            } catch (IOException e) {
                if (running) {
                    System.err.println("Error reading message: " + e.getMessage());
                }
            } finally {
                close();
            }
        });
        listenerThread.start();
    } // startListener

    /**
     * Handles an incoming message from the server.
     * <p>
     * Override this method to implement custom message handling.
     *
     * @param message the message received from the server
     */
    protected void handleMessage(String message) {
        System.out.println("Received: " + message);
    } // handleMessage

    /**
     * Sends a message to the server.
     *
     * @param message the message to send
     */
    public void sendMessage(String message) {
        out.println(message);
    } // sendMessage

    /**
     * Closes the socket connection and stops the listener thread.
     */
    public void close() {
        running = false;
        try {
            if (listenerThread != null && listenerThread.isAlive()) {
                listenerThread.interrupt();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing socket: " + e.getMessage());
        }
    } // close

    /**
     * Returns the underlying Socket.
     *
     * @return the Socket instance
     */
    public Socket getSocket() {
        return socket;
    } // getSocket
} // ClientSocketHandler