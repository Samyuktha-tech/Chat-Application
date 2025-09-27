import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Subject in Observer pattern: manages observers (clients), message history and user list.
 */
public class ChatRoom {
    private static final Logger logger = Logger.getLogger(ChatRoom.class.getName());

    private final String roomId;
    // username -> ChatClient
    private final ConcurrentMap<String, ChatClient> clients = new ConcurrentHashMap<>();
    private final List<Message> history = Collections.synchronizedList(new ArrayList<>());
    private final ExecutorService notifier = Executors.newCachedThreadPool();

    public ChatRoom(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() { return roomId; }

    // join with a client; if username exists, returns false
    public boolean join(ChatClient client) {
        if (clients.putIfAbsent(client.getUsername(), client) == null) {
            broadcastSystem(String.format("%s joined the room", client.getUsername()));
            logger.info(client.getUsername() + " joined " + roomId);
            return true;
        } else {
            logger.warning("Attempt to join with duplicate username: " + client.getUsername());
            return false;
        }
    }

    public void leave(String username) {
        ChatClient removed = clients.remove(username);
        if (removed != null) {
            removed.disconnect();
            broadcastSystem(String.format("%s left the room", username));
            logger.info(username + " left " + roomId);
        }
    }

    public List<String> activeUsers() {
        return new ArrayList<>(clients.keySet());
    }

    public List<Message> getHistory(int maxMessages) {
        synchronized (history) {
            int from = Math.max(0, history.size() - maxMessages);
            return new ArrayList<>(history.subList(from, history.size()));
        }
    }

    public void publishPublicMessage(String fromUsername, String content) {
        Message msg = new Message(UUID.randomUUID().toString(), fromUsername, null, content, Message.Type.PUBLIC);
        storeAndNotify(msg);
    }

    public void publishPrivateMessage(String fromUsername, String toUsername, String content) {
        Message msg = new Message(UUID.randomUUID().toString(), fromUsername, toUsername, content, Message.Type.PRIVATE);
        // store history but only notify the two involved parties
        synchronized (history) { history.add(msg); }
        notifyPrivate(msg);
    }

    private void storeAndNotify(Message msg) {
        synchronized (history) { history.add(msg); }
        String payload = msg.toString();
        // notify all clients concurrently
        for (ChatClient c : clients.values()) {
            notifier.execute(() -> c.notify(payload));
        }
    }

    private void notifyPrivate(Message msg) {
        String payload = msg.toString();
        ChatClient toClient = clients.get(msg.getTo());
        ChatClient fromClient = clients.get(msg.getFrom());
        if (toClient != null) {
            notifier.execute(() -> toClient.notify(payload));
        }
        if (fromClient != null) {
            notifier.execute(() -> fromClient.notify(payload));
        }
    }

    private void broadcastSystem(String systemMessage) {
        Message msg = new Message(UUID.randomUUID().toString(), "SYSTEM", null, systemMessage, Message.Type.SYSTEM);
        storeAndNotify(msg);
    }

    public void shutdown() {
        notifier.shutdown();
        clients.values().forEach(ChatClient::disconnect);
        clients.clear();
    }
}
