import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
                outToClient.writeBytes(request);
                System.out.println("Sent: " + request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
