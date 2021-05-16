package tcp;

import java.io.*;
import java.net.*;

public class TCPClient {
    private Socket clientSocket;
    private DataOutputStream writeOut;

    public void connect(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            writeOut = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendNum(int number){
        try {
            writeOut.writeInt(number);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send() {
        for (int i = 0; i <= 1000; i++) {
            System.out.println(i);
            sendNum(i);
        }
    }

    public void stop() {
        try {
            writeOut.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
