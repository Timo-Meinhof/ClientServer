import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class UDPServer extends Thread {

    private DatagramSocket socket;
    private boolean running = true;
    private byte[] buffer = new byte[4];

    public UDPServer(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(running) {
            DatagramPacket pkt = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(pkt);

                byte[] readByte = pkt.getData();
                ByteBuffer wrapped = ByteBuffer.wrap(readByte);
                int readPrev = -1, read = wrapped.getInt();
                while (read != -1) {
                    read = wrapped.getInt();
                    if (readPrev + 1 != read) {
                        System.out.println("Fehler: Diese Zahl war nicht fortlaufend: " + read);
                    }
                    System.out.println(read);
                    readPrev = read;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopServer(){
        running = false;
    }
}
