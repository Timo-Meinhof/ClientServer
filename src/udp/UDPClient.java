package udp;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

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
        ByteBuffer intBuffer = ByteBuffer.allocate(4);
        intBuffer.putInt(number);
        buffer = intBuffer.array();

        DatagramPacket pkt = new DatagramPacket(buffer, buffer.length, address, port);
        try {
            socket.send(pkt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(){
        long startTime = System.nanoTime();
        for (int i = 0; i <= 1000000; i++) sendNum(i);
        long endTime = System.nanoTime();
        System.out.println("Message sent. Took " + (endTime - startTime)/1000000000.0 + "s.");
    }

    public void stop() {
        socket.close();
    }

    private static boolean isValidIP(String ip) {
        String[] groups = ip.split("\\.");
        if (groups.length != 4) return false;
        try {
            return Arrays.stream(groups).filter(s -> s.length() > 1 && s.startsWith("0"))
                    .map(Integer::parseInt).filter(i -> (i >= 0 && i <= 255)).count() == 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static byte[] getIPfromString(String ip){
        String[] groups = ip.split("\\.");
        byte[] result = new byte[4];
        for(int i = 0; i < 4; i++){
            result[i] = Byte.parseByte(groups[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        int port = 3080;
        String ip = "127.0.0.1";
        boolean isIP = true;

        if (args.length > 2)
            System.out.println("Too many arguments. Using default ip (127.0.0.1) and port (3080)");
        else if (args.length < 2)
            System.out.println("Port or IP missing. Using default ip (127.0.0.1) and port (3080)");
        else {
            ip = args[0];
            if(!isValidIP(ip)) {
                System.out.println("No valid IP detected. Interpreting first argument as name");
                isIP = false;
            }
            try {
                int tmp_port = Integer.parseInt(args[1]);
                if (tmp_port > 65535 || tmp_port < 1)
                    System.out.println("Given integer value is not a valid port. Using default port (3080)");
                else if (tmp_port < 1024) System.out.println("Given port is a system port. Using default port (3080)");
                else port = tmp_port;
            } catch (NumberFormatException ex) {
                System.out.println("Given argument wasn't an integer");
            }
        }

        UDPClient udpClient;
        if(isIP){
            udpClient = new UDPClient(getIPfromString(ip), port);
        }else{
            udpClient = new UDPClient(ip, port);
        }

        udpClient.send();
        udpClient.stop();
    }
}
