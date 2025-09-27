/**
 * Adapter interface: abstracts how messages are delivered to a client
 */
public interface ProtocolAdapter {
    /**
     * Send a text payload to client identified by clientId.
     * Implementations can use WebSocket, HTTP Long Polling, etc.
     */
    void sendToClient(String clientId, String payload);

    /**
     * Called when an adapter is shutting down or client disconnects — allows cleanup.
     */
    default void close(String clientId) {}
}
