package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class UDPServer {

    private DatagramSocket socket;
    private byte[] buffer = new byte[4];

    public void start(int port) {
        try {
            socket = new DatagramSocket(port);
            System.out.println("Server started. Waiting for messages ...");
            while(true) {
                DatagramPacket pkt = new DatagramPacket(buffer, buffer.length);
                try {
                    int previous = -1;
                    while (true) {
                        socket.receive(pkt);

                        byte[] readByte = pkt.getData();
                        ByteBuffer byteBuffer = ByteBuffer.wrap(readByte);
                        int read = byteBuffer.getInt();

                        // EOF is encoded as -1 - Terminate connection to this client once -1 is received
                        if (read == -1) break;

                        // Coherent check
                        if (read != previous + 1 && previous != 1000000)
                            System.out.println("Error: Numbers not coherent! " + previous + " was followed by " + read + ".");

                        previous = read;

                        if (read == 1000000) {
                            System.out.println("A full set of numbers was received!");
                            previous = -1;
                        }
                    }
                } catch (IOException e) {
                    // Couldn't receive packet
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            // Couldn't attach to port
            e.printStackTrace();
        }
    }

    public void closeConnections() {
        socket.close();
    }

    public static void main(String[] args) {
        UDPServer udpServer = new UDPServer();

        int port = 3080;

        if(args.length > 1)
            System.out.println("Too many arguments. Using default port (3080)");
        else if(args.length < 1)
            System.out.println("No port given. Using default port (3080)");
        else {
            try{
                int tmp_port = Integer.parseInt(args[0]);
                if(tmp_port > 65535 || tmp_port < 1)
                    System.out.println("Given integer value is not a valid port. Using default port (3080)");
                else if(tmp_port < 1024)
                    System.out.println("Given port is a system port. Using default port (3080)");
                else
                    port = tmp_port;
            }
            catch (NumberFormatException ex){
                System.out.println("Given argument wasn't an integer");
            }
        }

        udpServer.start(port);
    }
}
