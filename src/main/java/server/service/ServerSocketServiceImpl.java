package server.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketServiceImpl implements ServerSocketService {

    @Override
    public ServerSocket createServerSocket(int PORT) {
        ServerSocket serverSocket = null; //not good - but this is minimal server :)
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Can't create server socket on port " + PORT);
            e.printStackTrace();
        }
        return serverSocket;
    }

    @Override
    public void closerServerSocket(ServerSocket serverSocket) {
        try {
            serverSocket.close();

        } catch (IOException e) {
            System.out.println("Can't close server socket");
            e.printStackTrace();
        }
    }

    @Override
    public Socket getClientSocket(ServerSocket serverSocket) {
        Socket socket = null; // not good - but this is minimal server :)
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }
}
