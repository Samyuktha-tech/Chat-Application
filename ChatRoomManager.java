import java.util.HashMap;
import java.util.Map;

public class ChatRoomManager {
    private Map<String, ChatRoom> rooms = new HashMap<>();

    // Public constructor so it can be used in ServerSimulator
    public ChatRoomManager() {}

    public ChatRoom getOrCreateRoom(String roomName) {
        return rooms.computeIfAbsent(roomName, ChatRoom::new);
    }
}
