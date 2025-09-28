import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private String roomName;
    private List<ChatClient> clients;

    public ChatRoom(String roomName) {
        this.roomName = roomName;
        this.clients = new ArrayList<>();
    }

    public String getRoomName() {
        return roomName;
    }

    public void addClient(ChatClient client) {
        clients.add(client);
        System.out.println(client.getName() + " joined the room " + roomName);
    }

    public void broadcastMessage(ChatClient sender, String message) {
        for (ChatClient client : clients) {
            if (!client.equals(sender)) {
                client.receiveMessage(sender.getName() + ": " + message);
            }
        }
    }
    public void showActiveUsers() {
    System.out.println("Active users in " + roomName + ":");
    for (ChatClient client : clients) {
        System.out.println("- " + client.getName());
    }
}

}
