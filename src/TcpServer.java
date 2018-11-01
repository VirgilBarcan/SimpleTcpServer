import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer implements Runnable {

    private static final int SERVER_PORT = 5991;

    private ServerSocket socket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private boolean isStopped;

    public TcpServer() {
        try {
            socket = new ServerSocket(SERVER_PORT);
            isStopped = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void stop() {
        isStopped = true;
    }

    private synchronized boolean isStopped() {
        return isStopped;
    }

    private void processClient() {
        while (!isStopped()) {
            try {
                String message = receiveMessage();
                sendMessage(message);
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String receiveMessage() throws IOException {
        String request = inFromClient.readLine();
        System.out.println("Received: " + request);
        return request;
    }

    public void sendMessage(String message) throws IOException {
        outToClient.writeBytes(message + "\n");
        outToClient.flush();
        System.out.println("Sent: " + message);
    }

    @Override
    public void run() {
        try {
            System.out.println("Starting server...");
            Socket clientSocket = socket.accept();
            inFromClient =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToClient = new DataOutputStream(clientSocket.getOutputStream());
            processClient();
            System.out.println("Server stopped.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
