public class TestingServerTCP {

    public static void main(String[] args) {
        TCPServer tcpServer = new TCPServer();
        tcpServer.start(3080);

        tcpServer.stop();
    }
}
