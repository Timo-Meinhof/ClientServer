package tcp;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream readIn;

    /*
    Frage 1: Welcher Unterschied lässt sich bei der Ausführung der UDP- und der TCP-Variante feststellen?
    Antwort:
            Die Ausführung bei TCP dauert bedeutend länger (~30min vs. 30s)

    Frage 2: Welche Fehlermeldungen gibt Java aus, wenn Sie:
             -  versuchen, die Server-Applikationen zweimal auf dem gleichen Rechner zu starten
                (TCP/TCP, UDP/UDP, TCP/UDP)? Warum?
    Antwort: TCP/TCP
                java.net.BindException: Address already in use: JVM_Bind
                Es können niemals zwei TCP Server auf der gleichen Port laufen
             UDP/UDP
                java.net.BindException: Address already in use: Cannot bind
                Es können niemals zwei UDP Server auf der gleichen Port laufen
             TCP/UDP
                Kein Problem/Fehler.

            - eine Client-Applikation mit dem Namen eines Computers als Server, den es nicht gibt, starten?
    Antwort: Fehler: java.net.UnknownHostException: Der angegebene Host ist unbekannt (macFlownowMcFlowneloo)

            - die Client-Applikation mit dem Namen eines Rechners starten, auf dem der Server nicht läuft?
    Antwort: UDP:
             Sendet trotzdem. Wirft keine Fehler. Auf dem Server passiert logischerweise nichts :)
             TCP:
             Fehler: java.net.ConnectException: Connection refused: connect

     */

    public void start(int port) {
        // Try catch for "server start"
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started. Waiting for connection ...");
            while (true) {
                try {
                    clientSocket = serverSocket.accept();
                    System.out.println("Connection accepted.");
                    readIn = new DataInputStream(clientSocket.getInputStream());

                    // Starting at 0 (First previous is 0 - 1 = -1)
                    int previous = -1;

                    // Keep accepting
                    while (true) {
                        try {
                            int read = readIn.readInt();
                            // EOF is encoded as -1 - Terminate connection to this client once -1 is received
                            if (read == -1) break;

                            // Coherent check
                            if (read != previous + 1)
                                System.out.println("Error: Numbers not coherent! " + previous + " was followed by " + read + ".");

                            previous = read;
                        } catch(EOFException e){
                            System.out.println("Message received.");
                            break;
                        }
                    }

                    // Terminate connection to current client
                    clientSocket.close();
                    System.out.println("Connection closed. Waiting for new connection ...");
                } catch (IOException e) {
                    // Couldn't accept connection. Wait for new connection.
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            // Couldn't attach to port
            e.printStackTrace();
        }
    }

    // This method could be called from outside to close all server connections correctly
    public void closeConnections() {
        try {
            readIn.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 3080;

        if(args.length > 1)
            System.out.println("Too many arguments. Using default port (3080)");
        else if(args.length < 1)
            System.out.println("No port given. Using default port (3080)");
        else {
            try{
                int tmp_port = Integer.parseInt(args[0]);
                if(tmp_port > 65535 || tmp_port < 1)
                    System.out.println("Given integer value is not a valid port. Using default port (3080)");
                else if(tmp_port < 1024)
                    System.out.println("Given port is a system port. Using default port (3080)");
                else
                    port = tmp_port;
            }
            catch (NumberFormatException ex){
                System.out.println("Given argument wasn't an integer");
            }
        }

        TCPServer tcpServer = new TCPServer();
        tcpServer.start(port);
    }
}
