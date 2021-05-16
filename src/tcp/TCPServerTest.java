package tcp;

public class TCPServerTest {

    public static void main(String[] args) {
        TCPServer tcpServer = new TCPServer();
        tcpServer.start(3080);
    }

}
