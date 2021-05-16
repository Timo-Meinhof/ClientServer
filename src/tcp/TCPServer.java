package tcp;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream readIn;


    public void start(int port) {
        // Try catch for "server start"
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started. Waiting for connection ...");
            while (true) {
                try {
                    clientSocket = serverSocket.accept();
                    System.out.println("Connection accepted.");
                    readIn = new DataInputStream(clientSocket.getInputStream());

                    // Starting at 0 (First previous is 0 - 1 = -1)
                    int previous = -1;

                    // Keep accepting
                    while (true) {
                        int read = readIn.readInt();
                        System.out.println(read);
                        // EOF is encoded as -1 - Terminate connection to this client once -1 is received
                        if (read == -1) break;

                        // Coherent check
                        if (read != previous + 1)
                            System.out.println("Error: Numbers not coherent! " + previous + " was followed by " + read + ".");

                        previous = read;
                    }

                    // Terminate connection to current client
                    clientSocket.close();
                    System.out.println("Connection closed. Waiting for new connection ...");
                } catch (IOException e) {
                    // Couldn't accept connection. Wait for new connection.
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            // Couldn't attach to port
            e.printStackTrace();
        }
    }

    // This method could be called from outside to close all server connections correctly
    public void closeConnections() {
        try {
            readIn.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
