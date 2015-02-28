package pro.beam.api.resource.chat.events.data;

import com.google.gson.annotations.SerializedName;
import pro.beam.api.resource.chat.events.GenericChatEvent;

import java.util.List;

public class IncomingMessageData extends GenericChatEvent.EventData {
    public int channel;
    public String id;
    public String user_name;
    public String user_id;
    public String user_role;
    public List<MessagePart> message;

    public static class MessagePart {
        public Type type;
        public String data;
        public String path;

        public static enum Type {
            @SerializedName("text") TEXT,
            @SerializedName("emoticon") EMOTICON;
        }
    }
}
