package demo.great.zhang.railwayvideo.eventbus;

public class Message {
    public final int message;

    public static Message getInstance(int message) {
        return new Message(message);
    }

    public Message(int message) {
        this.message = message;
    }
}
