public class ChatClient {
    private String name;

    public ChatClient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void sendMessage(ChatRoom room, String message) {
        room.broadcastMessage(this, message);
    }

    public void sendPrivateMessage(ChatClient recipient, String message) {
        recipient.receiveMessage("(Private) " + this.name + ": " + message);
    }

    public void receiveMessage(String message) {
        System.out.println(name + " received: " + message);
    }
}
