package server.service;

import server.data.Message;
import server.data.MessageHolder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ServerService {
    private static final int PORT = 8000;
    private final List<ClientHandler> clients = new ArrayList<>();
    private Socket clientSocket;
    private final ServerSocket serverSocket;
    private final MessageHolder messageHolder;

    public ServerService() {
        serverSocket = createServerSocket();
        messageHolder = new MessageHolder();
        while (true) {
            clientSocket = getClientSocket();
            ClientHandler clientHandler = new ClientHandler(clientSocket, this);
            clients.add(clientHandler);
            new Thread(clientHandler).start();
        }

    }

    private ServerSocket createServerSocket() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Can't create server socket on port " + PORT);
            e.printStackTrace();
        }
        return serverSocket;
    }


    private void closeServerSocket() {
        try {
            serverSocket.close();

        } catch (IOException e) {
            System.out.println("Can't close server socket");
            e.printStackTrace();
        }
    }


    private Socket getClientSocket() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    public void sendMessageToAllClients(ClientHandler clientHandler, String message) {
        String messageLine = messageHolder.saveMessage(new Message(clientHandler.getClientName(),
                message,
                new Timestamp(System.currentTimeMillis())));

        for (ClientHandler c : clients) {
            c.sendMessage(messageLine);
        }
    }

    public void sendAllMessagesToClient(ClientHandler clientHandler) {
        clientHandler.sendMessage(messageHolder.getAllMessages());
    }


    public void deleteMessage(ClientHandler clientHandler, int index) {
        messageHolder.deleteMessage(index, clientHandler.getClientName());
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}
