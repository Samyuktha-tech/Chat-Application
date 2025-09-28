public class ServerSimulator {
    public static void main(String[] args) {
        ChatRoomManager manager = new ChatRoomManager();
        ChatRoom room = manager.getOrCreateRoom("Room123");
        

        ChatClient alice = new ChatClient("Alice");
        ChatClient bob = new ChatClient("Bob");
        ChatClient charlie = new ChatClient("Charlie");

        room.addClient(alice);
        room.addClient(bob);
        room.addClient(charlie);

        alice.sendMessage(room, "Hello, everyone!");
        bob.sendMessage(room, "How's it going?");
        charlie.sendPrivateMessage(alice, "Hi Alice, let's talk privately.");
    }
}
