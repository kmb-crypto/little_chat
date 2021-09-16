package server.service;

import java.net.ServerSocket;
import java.net.Socket;

public interface ServerSocketService {
    ServerSocket createServerSocket(int port);

    void closerServerSocket(ServerSocket serverSocket);

    Socket getClientSocket(ServerSocket serverSocket);

}
