import java.util.logging.*;

public class ServerSimulator {
    public static void main(String[] args) throws InterruptedException {
        // 🔹 Step 1: Disable default INFO logs for clean output
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        for (Handler h : handlers) {
            h.setLevel(Level.SEVERE);  // Only show severe errors
        }
        rootLogger.setLevel(Level.SEVERE);

        // 🔹 Step 2: Create ChatRoom and Clients
        ChatRoomManager manager = ChatRoomManager.getInstance();
        ChatRoom room = manager.getOrCreateRoom("Room123");

        Client alice = new Client("Alice", new WebSocketAdapter());
        Client bob = new Client("Bob", new HttpAdapter());
        Client charlie = new Client("Charlie", new WebSocketAdapter());

        room.join(alice);
        room.join(bob);
        room.join(charlie);

        // 🔹 Step 3: Show active users
        System.out.println("\n===== ACTIVE USERS =====");
        System.out.println(room.activeUsers());

        // 🔹 Step 4: Public messages demo
        System.out.println("\n===== PUBLIC CHAT DEMO =====");
        room.publishPublicMessage("Alice", "Hello, everyone!");
        room.publishPublicMessage("Bob", "How's it going?");

        // 🔹 Step 5: Private messaging demo
        System.out.println("\n===== PRIVATE CHAT DEMO =====");
        room.publishPrivateMessage("Charlie", "Alice", "Hey Alice, can you check line 42?");

        // 🔹 Step 6: Show message history
        System.out.println("\n===== MESSAGE HISTORY =====");
        room.getHistory(50).forEach(m -> System.out.println(m.toString()));

        // 🔹 Step 7: Exit demo
        System.out.println("\n===== DEMO COMPLETE =====");
    }
}
