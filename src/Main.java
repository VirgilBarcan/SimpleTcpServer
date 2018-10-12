public class Main {

    public static void main(String[] args) {
        TcpServer tcpServer = new TcpServer();

        if (args.length == 2) {
            tcpServer.SetTimeBetweenResponses(Integer.parseInt(args[0]));
            tcpServer.SetNoOfResponses(Integer.parseInt(args[1]));
        }

        System.out.println("Starting server...");
        tcpServer.Run();
        System.out.println("Server stopped.");
    }

}
