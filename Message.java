import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
    public enum Type { PUBLIC, PRIVATE, SYSTEM }

    private final String id;
    private final String from;
    private final String to; // null for public
    private final String content;
    private final Instant timestamp;
    private final Type type;

    public Message(String id, String from, String to, String content, Type type) {
        this.id = Objects.requireNonNull(id);
        this.from = from;
        this.to = to;
        this.content = Objects.requireNonNull(content);
        this.type = type;
        this.timestamp = Instant.now();
    }

    public String getId() { return id; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public String getContent() { return content; }
    public Instant getTimestamp() { return timestamp; }
    public Type getType() { return type; }

    @Override
    public String toString() {
        String ts = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault()).format(timestamp);
        if (type == Type.PRIVATE) {
            return String.format("[%s] (private) %s -> %s: %s", ts, from, to, content);
        } else if (type == Type.SYSTEM) {
            return String.format("[%s] (system): %s", ts, content);
        } else {
            return String.format("[%s] %s: %s", ts, from, content);
        }
    }
}
