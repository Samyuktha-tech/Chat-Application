import java.util.logging.Logger;

/**
 * A simulated HTTP long-polling adapter.
 * For demo purposes it behaves similarly to WebSocketAdapter but shows adaptation capability.
 */
public class HttpAdapter implements ProtocolAdapter {
    private static final Logger logger = Logger.getLogger(HttpAdapter.class.getName());

    @Override
    public void sendToClient(String clientId, String payload) {
        // In a real HTTP long poll, we would push the payload on the pending response.
        logger.info("[HttpAdapter] push to " + clientId + ": " + payload);
        System.out.println("[HTTP][" + clientId + "] " + payload);
    }
}
