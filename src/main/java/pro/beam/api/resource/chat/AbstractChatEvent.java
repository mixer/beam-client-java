package pro.beam.api.resource.chat;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract class AbstractChatEvent<T extends AbstractChatEvent.EventData> extends AbstractChatDatagram {
    public AbstractChatEvent() {
        this.type = Type.EVENT;
    }

    public EventType event;
    public List<T> data;

    public static abstract class EventData {}
    public static enum EventType {
        @SerializedName("ChatMessage") CHAT_MESSAGE,
        @SerializedName("PollStart") POLL_START,
        @SerializedName("PollEnd") POLL_END,
    }
}
