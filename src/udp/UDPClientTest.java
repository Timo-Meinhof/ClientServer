package udp;

public class UDPClientTest {

    public static void main(String[] args) {
        UDPClient udpClient = new UDPClient("localhost", 3090);
        udpClient.send();
        udpClient.stop();
    }

}
