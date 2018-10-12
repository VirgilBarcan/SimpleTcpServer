import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    private static final int SERVER_PORT = 4991;

    private ServerSocket socket;

    public TcpServer() {
        try {
            socket = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Run() {
        String request;
        while (true) {
            try {
                Socket clientSocket = socket.accept();

                BufferedReader inFromClient =
                        new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());

                request = inFromClient.readLine();
                System.out.println("Received: " + request);

                request += "\n";

                int responses = 100;
                while (--responses > 0) {
                    outToClient.writeBytes(request);
                    outToClient.flush();
                    System.out.println("Sent: " + request);

                    Thread.sleep(80000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
