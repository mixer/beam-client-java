package pro.beam.api.resource.chat.events.data;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterators;
import com.google.gson.annotations.SerializedName;
import pro.beam.api.resource.chat.AbstractChatEvent;

import java.util.List;

public class IncomingMessageData extends AbstractChatEvent.EventData {
    public int channel;
    public String id;
    public String user_name;
    public String user_id;
    public String user_role;
    public List<MessagePart> message;

    public String getMessage() {
        return Joiner.on(' ').join(Iterators.transform(this.message.iterator(), new Function<MessagePart, String>() {
            @Override public String apply(MessagePart part) {
                return part.type == MessagePart.Type.TEXT ? part.data: part.url;
            }
        }));
    }

    public static class MessagePart {
        public Type type;
        public String url;
        public String data;
        public String path;

        public static enum Type {
            @SerializedName("text") TEXT,
            @SerializedName("emoticon") EMOTICON,
            @SerializedName("link") LINK;
        }
    }
}
