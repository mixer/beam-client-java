package pro.beam.api.resource.chat;

import com.google.gson.annotations.SerializedName;
import pro.beam.api.resource.chat.events.*;

public abstract class AbstractChatEvent<T extends AbstractChatEvent.EventData> extends AbstractChatDatagram {
    public AbstractChatEvent() {
        this.type = Type.EVENT;
    }

    public EventType event;
    public T data;

    public static abstract class EventData {}
    public static enum EventType {
        @SerializedName("WidgetMessage") WIDGET_MESSAGE (IncomingWidgetEvent.class),
        @SerializedName("ChatMessage") CHAT_MESSAGE (IncomingMessageEvent.class),
        @SerializedName("PollStart") POLL_START (PollStartEvent.class),
        @SerializedName("PollEnd") POLL_END (PollEndEvent.class),
        @SerializedName("Stats") STATS (StatusEvent.class),
        @SerializedName("UserJoin") USER_JOIN (UserJoinEvent.class),
        @SerializedName("UserLeave") USER_LEAVE (UserLeaveEvent.class);

        private final Class<? extends AbstractChatEvent> correspondingClass;

        private EventType(Class<? extends AbstractChatEvent> correspondingClass) {
            this.correspondingClass = correspondingClass;
        }

        public static EventType fromSerializedName(String name) {
            if (name == null) return null;

            for (EventType type : EventType.values()) {
                try {
                    String serializedName = type.getClass().getField(type.name())
                                                           .getAnnotation(SerializedName.class).value();
                    if (name.equals(serializedName)) {
                        return type;
                    }
                } catch (NoSuchFieldException e) {
                    return null;
                }
            }

            return null;
        }

        public Class<? extends AbstractChatEvent> getCorrespondingClass() {
            return this.correspondingClass;
        }
    }
}
