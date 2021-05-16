import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream readIn;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                clientSocket = serverSocket.accept();
                readIn = new DataInputStream(clientSocket.getInputStream());

                int readPrev = -1;
                while (true) {
                    int read = readIn.read();
                    if (read == -1) break;
                    if (readPrev + 1 != read) {
                        System.out.println("Fehler: Diese Zahl war nicht fortlaufend: " + read);
                    }
                    readPrev = read;
                }
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        try {
            readIn.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
