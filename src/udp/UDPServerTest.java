package udp;

public class UDPServerTest {

    public static void main(String[] args) {
        UDPServer udpServer = new UDPServer();
        udpServer.start(3090);
    }

}
