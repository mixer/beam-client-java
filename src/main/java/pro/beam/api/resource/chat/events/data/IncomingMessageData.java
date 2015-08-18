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
    public String user_id;
    public List<BeamUser.Role> user_roles;
    public List<MessagePart> message;

    @Deprecated()
    public String getMessage() {
        return this.asString();
    }

    public String asString() {
        return Joiner.on("").join(Iterators.transform(this.message.iterator(), new Function<MessagePart, String>() {
            @Override public String apply(MessagePart part) {
                switch(part.type) {
                    case ME:
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
        public Type type;
        public String url;
        public String data;
        public String path;
        public String text;

        // Emoticon-related
        public String source;
        public String pack;
        public Coords coords;

        public static class Coords {
            public int x;
            public int y;
        }

        public static enum Type {
            @SerializedName("me") ME,
            @SerializedName("text") TEXT,
            @SerializedName("emoticon") EMOTICON,
            @SerializedName("link") LINK,
        }
    }
}
