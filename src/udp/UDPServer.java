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
}
