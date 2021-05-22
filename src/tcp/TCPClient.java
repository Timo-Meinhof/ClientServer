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

    private void sendNum(int number) {
        try {
            writeOut.writeInt(number);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send() {
        long startTime = System.nanoTime();
        for (int i = 0; i <= 1000; i++) sendNum(i);
        long endTime = System.nanoTime();
        System.out.println("Message sent. Took " + (endTime - startTime)/1000000000.0 + "s.");
    }

    public void stop() {
        try {
            writeOut.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        int port = 3080;
        String ip = "127.0.0.1";
        boolean isIP = true;


        if (args.length > 2)
            System.out.println("Too many arguments. Using default ip (" + ip + ") and port (" + port + ")");
        else if (args.length < 2)
            System.out.println("Port or IP missing. Using default ip (" + ip + ") and port (" + port + ")");
        else {
            try {
                int tmp_port = Integer.parseInt(args[1]);
                if (tmp_port > 65535 || tmp_port < 1)
                    System.out.println("Given integer value is not a valid port. Using default port (" + port + ")");
                else if (tmp_port < 1024) System.out.println("Given port is a system port. Using default port (" + port + ")");
                else port = tmp_port;
            } catch (NumberFormatException ex) {
                System.out.println("Given argument wasn't an integer");
            }
        }

        System.out.println("Connecting to " + ip + ":" + port);
        TCPClient tcpClient = new TCPClient();
        tcpClient.connect(ip, port);
        tcpClient.send();
        tcpClient.stop();
    }
}
