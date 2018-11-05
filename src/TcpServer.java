import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TcpServer implements Runnable {

    private static final int SERVER_PORT = 5991;

    private ServerSocket socket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private boolean isStopped;
    private Lock lock;
    private Condition condition;

    public TcpServer() {
        try {
            socket = new ServerSocket(SERVER_PORT);
            isStopped = false;
            lock = new ReentrantLock();
            condition = lock.newCondition();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void stop() {
        isStopped = true;
//        lock.lock();
//        condition.signal();
//        lock.unlock();
    }

    private synchronized boolean isStopped() {
        return isStopped;
    }

    private void processClient() {
        try {
            while (!isStopped()) {
                String message = receiveMessage();
                sendMessage(message);
                System.out.println();
            }

//            while (!isStopped()) {
//                // wait for messages to be sent
//                lock.lock();
//                condition.await();
//                lock.unlock();
//            }
        } catch (IOException e) {
            e.printStackTrace();
            lock.unlock();
        }
    }

    public String receiveMessage() throws IOException {
        String request = inFromClient.readLine();
        Instant instant = Instant.now();
        System.out.println("[" + instant + "] " + "Received: " + request);
        return request;
    }

    public void sendMessage(String message) throws IOException {
//        lock.lock();
//        condition.signal();
//        lock.unlock();

        outToClient.writeBytes(message + "\n");
        outToClient.flush();
        Instant instant = Instant.now();
        System.out.println("[" + instant + "] " + "Sent: " + message);
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
