package pro.beam.api.resource.chat.events.data;

import java.util.List;

import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.chat.AbstractChatEvent;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterators;
import com.google.gson.annotations.SerializedName;

public class IncomingMessageData extends AbstractChatEvent.EventData {
    public int channel;
    public String id;
    public String user_name;
    public int user_id;
    public List<BeamUser.Role> user_roles;
    public MessagePart message;

    @Deprecated()
    public String getMessage() {
        return this.asString();
    }

    public String asString() {
        return Joiner.on("").join(Iterators.transform(this.message.message.iterator(), new Function<MessagePart.MessageMessagePart, String>() {
            @Override public String apply(MessagePart.MessageMessagePart part) {
                switch(part.type) {
                    case EMOTICON:
                        return part.text;
                    case LINK:
                        return part.url;
                    case TEXT:
                    default:
                        return part.data;
                }
            }
        }));
    }

    public static class MessagePart {
        public MessageMetaPart meta;
        public List<MessageMessagePart> message;

        public static class MessageMetaPart {
            public boolean me;
        }

        public static class MessageMessagePart {
            public Type type;
            public String data;
            public String text;

            // Emoticon-related
            public String source;
            public String pack;
            public Coords coords;

            // Link-related
            public String url;

            public static class Coords {
                public int x;
                public int y;
            }

            public enum Type {
                @SerializedName("text")TEXT,
                @SerializedName("emoticon")EMOTICON,
                @SerializedName("link")LINK,
            }
        }
    }
}
