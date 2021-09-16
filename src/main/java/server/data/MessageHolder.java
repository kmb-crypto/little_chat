package server.data;

import java.util.concurrent.CopyOnWriteArrayList;

public class MessageHolder {
    CopyOnWriteArrayList<Message> messages = new CopyOnWriteArrayList<>();
}
