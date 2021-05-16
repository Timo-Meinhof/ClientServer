public class TestingServerUDP {

    public static void main(String[] args) {
        UDPServer udpServer = new UDPServer(3090);
        udpServer.start();

        udpServer.stopServer();
    }
}
