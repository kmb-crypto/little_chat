package server.data;

import java.util.concurrent.CopyOnWriteArrayList;

public class MessageHolder {
    CopyOnWriteArrayList<Message> messages = new CopyOnWriteArrayList<>();

    public String getAllMessages() {
        StringBuilder allMessages = new StringBuilder("");
        for (int i = 0; i < messages.size(); i++) {
            allMessages.append(createMessageLine(i, messages.get(i))).append("\n");
        }
        return allMessages.toString();
    }

    public String saveMessage(Message message) {
        messages.add(message);
        return createMessageLine(messages.indexOf(message), message);
    }

    public void deleteMessage(int index, String clientName) {
        Message message = messages.get(index);
        if (message.getName().equals(clientName)) {
            messages.set(index, new Message(clientName, "DELETED", message.getTime()));
        }
    }

    private String createMessageLine(int id, Message message) {
        StringBuilder messageLine = new StringBuilder();
        messageLine.append(id).append(" ");
        messageLine.append(message.getName()).append(" -> ");
        messageLine.append(message.getText()).append(" | ");
        messageLine.append(message.getTime().toString());
        return messageLine.toString();
    }
}
