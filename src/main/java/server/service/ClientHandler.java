package server.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private ServerService serverService;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private Socket socket;
    private String clientName;

    public ClientHandler(Socket socket, ServerService serverService) {
        try {
            this.serverService = serverService;
            this.socket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (inMessage.hasNext()) {  //lets first message must be name
            clientName = inMessage.nextLine();
            serverService.sendAllMessagesToClient(this);
        }
        try {
            while (true) {
                if (inMessage.hasNext()) {
                    String message = inMessage.nextLine();
                    if (message.equalsIgnoreCase("##end##session##")) {
                        break;
                    }
                    if (message.toLowerCase().contains("##delete##")) {  // delete command example "23##delete##" or "##delete##23"
                        int index = 0;
                        try {
                            index = Integer.parseInt(message.toLowerCase().replace("##delete##", ""));
                        } catch (NumberFormatException e) {
                            sendMessage("Wrong delete command");
                        }
                        serverService.deleteMessage(this, index);
                    }
                    serverService.sendMessageToAllClients(this, message);
                }
                Thread.sleep(10);
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
    }

    private void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverService.removeClient(this);
    }


    public void sendMessage(String message) {
        outMessage.println(message);
        outMessage.flush();
    }

    public String getClientName() {
        return clientName;
    }
}
