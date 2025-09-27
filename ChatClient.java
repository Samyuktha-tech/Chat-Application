import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Observer. Represents a connected user session.
 */
public class ChatClient {
    private static final Logger logger = Logger.getLogger(ChatClient.class.getName());

    private final String clientId; // session id or unique token
    private final String username; // display name
    private ProtocolAdapter adapter;

    public ChatClient(String username, ProtocolAdapter adapter) {
        this.clientId = UUID.randomUUID().toString();
        this.username = Objects.requireNonNull(username);
        this.adapter = Objects.requireNonNull(adapter);
    }

    public String getClientId() { return clientId; }
    public String getUsername() { return username; }

    public void setAdapter(ProtocolAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * Called by ChatRoom when there is a new message or system update.
     */
    public void notify(String payload) {
        try {
            adapter.sendToClient(clientId, payload);
        } catch (Exception e) {
            logger.severe("Failed to notify client " + username + ": " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            adapter.close(clientId);
        } catch (Exception e) {
            logger.warning("Error closing adapter for " + username + ": " + e.getMessage());
        }
    }
}
