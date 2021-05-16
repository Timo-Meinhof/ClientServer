import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class TCPClient {
    private Socket clientSocket;
    private DataOutputStream writeOut;
    // private BufferedReader readIn;

    public void connect(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            writeOut = new DataOutputStream(clientSocket.getOutputStream());
            // readIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send() {
        for (int i = 0; i <= 100000; i++) {
            System.out.println(i);
            try {
                writeOut.write(i);
                writeOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        try {
            writeOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
