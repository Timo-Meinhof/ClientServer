import java.util.Scanner;

public class TestingClient {

    public static void main(String[] args) {
        TCPClient tcpClient = new TCPClient();
        tcpClient.connect("localhost", 3080);
        tcpClient.send();

        tcpClient.stop();

        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine();

        for (int i = 0; i < 100; i++) {
            System.out.print("=");
        }

        UDPClient udpClient = new UDPClient("localhost", 3090);
        udpClient.send();

        udpClient.stop();
    }
}
