import java.io.IOException;
import java.util.Scanner;

public class Main {

    private TcpServer tcpServer;

    private void userInteraction() {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(command);

            if (command.equals("quit")) {
                tcpServer.stop();
                break;
            }

            try {
                tcpServer.sendMessage(command);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void work() {
        tcpServer = new TcpServer();
        Thread serverThread = new Thread(tcpServer);
        serverThread.start();

        userInteraction();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.work();
    }

}
