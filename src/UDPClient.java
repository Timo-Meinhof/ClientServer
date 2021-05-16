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

    public UDPClient(byte[] hostAddress, int port) {
        try {
            this.socket = new DatagramSocket();
            this.address = InetAddress.getByAddress(hostAddress);
            this.port = port;
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void sendNum(int number) {
        ByteBuffer dbuf = ByteBuffer.allocate(4);
        dbuf.putInt(number);
        byte[] bytes = dbuf.array();

        DatagramPacket pkt = new DatagramPacket(buffer, buffer.length, address, port);
        try {
            socket.send(pkt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(){
        for(int i = 1; i < 1000000; i++){
            sendNum(i);
        }
    }

    public void stop() {
        socket.close();
    }

}
