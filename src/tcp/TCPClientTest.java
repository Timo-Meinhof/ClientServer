package tcp;

public class TCPClientTest {
    public static void main(String[] args) {
        TCPClient tcpClient = new TCPClient();
        tcpClient.connect("localhost", 3080);
        tcpClient.send();
        tcpClient.stop();
    }
}
