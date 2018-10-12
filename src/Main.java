public class Main {

    public static void main(String[] args) {
        TcpServer tcpServer = new TcpServer();
        System.out.println("Starting server...");
        tcpServer.Run();
        System.out.println("Server stopped.");
    }

}
