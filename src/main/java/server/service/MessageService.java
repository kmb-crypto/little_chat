package server.service;

public interface MessageService {
    String receiveMessage();
    void sendMessage(String text);
    void persistMessage(int id);
    void deleteMessage(int id );
    void sendAllMessages(String text);

}
