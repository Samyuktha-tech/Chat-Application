import java.util.logging.Logger;

/**
 * A simulated WebSocket adapter.
 * In a real server you would integrate with a WebSocket library and map clientId to a session.
 */
public class WebSocketAdapter implements ProtocolAdapter {
    private static final Logger logger = Logger.getLogger(WebSocketAdapter.class.getName());

    @Override
    public void sendToClient(String clientId, String payload) {
        // In a real app: session.sendText(payload)
        logger.info("[WebSocketAdapter] sending to " + clientId + ": " + payload);
        // For simulation we just print to console
        System.out.println("[WS][" + clientId + "] " + payload);
    }

    @Override
    public void close(String clientId) {
        logger.info("[WebSocketAdapter] closing connection for " + clientId);
    }
}
