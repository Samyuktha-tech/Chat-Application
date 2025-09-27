import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

/**
 * Singleton manager for chat rooms.
 */
public class ChatRoomManager {
    private static final Logger logger = Logger.getLogger(ChatRoomManager.class.getName());

    private static volatile ChatRoomManager INSTANCE;
    private final ConcurrentMap<String, ChatRoom> rooms = new ConcurrentHashMap<>();

    private ChatRoomManager() {}

    public static ChatRoomManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ChatRoomManager.class) {
                if (INSTANCE == null) INSTANCE = new ChatRoomManager();
            }
        }
        return INSTANCE;
    }

    public ChatRoom createRoom(String roomId) {
        return rooms.computeIfAbsent(roomId, id -> {
            logger.info("Creating room: " + id);
            return new ChatRoom(id);
        });
    }

    public ChatRoom getRoom(String roomId) {
        return rooms.get(roomId);
    }

    public boolean removeRoom(String roomId) {
        ChatRoom room = rooms.remove(roomId);
        if (room != null) {
            room.shutdown();
            logger.info("Removed room: " + roomId);
            return true;
        }
        return false;
    }

    public boolean roomExists(String roomId) {
        return rooms.containsKey(roomId);
    }

    public ConcurrentMap<String, ChatRoom> listRooms() {
        return rooms;
    }
}
