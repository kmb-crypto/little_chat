package server;

import server.service.ServerSocketService;
import server.service.ServerSocketServiceImpl;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    private static final int PORT = 8000;
    private static ServerSocketService serverSocketService = new ServerSocketServiceImpl();


    public static void main(String[] args) {
        ServerSocket serverSocket = serverSocketService.createServerSocket(PORT);
        Socket clientSocket = null;
        while (true) {
            clientSocket = serverSocketService.getClientSocket(serverSocket);

        }

    }


}
