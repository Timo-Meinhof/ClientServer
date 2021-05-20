package udp;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

public class UDPClient {

    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private byte[] buffer;

    public UDPClient(String hostAddress, int port) {
        try {
            this.socket = new DatagramSocket();
            this.address = InetAddress.getByName(hostAddress);
            this.port = port;
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void sendNum(int number) {
        ByteBuffer intBuffer = ByteBuffer.allocate(4);
        intBuffer.putInt(number);
        buffer = intBuffer.array();

        DatagramPacket pkt = new DatagramPacket(buffer, buffer.length, address, port);
        try {
            socket.send(pkt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(){
        long startTime = System.nanoTime();
        for (int i = 0; i <= 1000; i++) sendNum(i);
        long endTime = System.nanoTime();
        System.out.println("Message sent. Took " + (endTime - startTime)/1000000000.0 + "s.");
    }

    public void stop() {
        socket.close();
    }

    public static void main(String[] args) {
        int port = 3080;
        String ip = "127.0.0.1";
        boolean isIP = true;
        System.out.println(args[0]);

        if (args.length > 2)
            System.out.println("Too many arguments. Using default ip (127.0.0.1) and port (3080)");
        else if (args.length < 2)
            System.out.println("Port or IP missing. Using default ip (127.0.0.1) and port (3080)");
        else {
            ip = args[0];

            try {
                int tmp_port = Integer.parseInt(args[1]);
                if (tmp_port > 65535 || tmp_port < 1)
                    System.out.println("Given integer value is not a valid port. Using default port (3080)");
                else if (tmp_port < 1024) System.out.println("Given port is a system port. Using default port (3080)");
                else port = tmp_port;
            } catch (NumberFormatException ex) {
                System.out.println("Given argument wasn't an integer");
            }
        }

        UDPClient udpClient = new UDPClient(ip, port);

        udpClient.send();
        udpClient.stop();
    }
}
