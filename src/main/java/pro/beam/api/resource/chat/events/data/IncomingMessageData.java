package pro.beam.api.resource.chat.events.data;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterators;
import com.google.gson.annotations.SerializedName;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.chat.AbstractChatEvent;

import java.util.List;

public class IncomingMessageData extends AbstractChatEvent.EventData {
    public int channel;
    public String id;
    public String user_name;
    public String user_id;
    public List<BeamUser.Role> user_roles;
    public List<MessagePart> message;

    public String getMessage() {
        return Joiner.on(' ').join(Iterators.transform(this.message.iterator(), new Function<MessagePart, String>() {
            @Override public String apply(MessagePart part) {
                switch(part.type) {
                    case ME:
                        return part.text;
                    case EMOTICON:
                        return part.path;
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

        public static enum Type {
            @SerializedName("me") ME,
            @SerializedName("text") TEXT,
            @SerializedName("emoticon") EMOTICON,
            @SerializedName("link") LINK,
        }
    }
}
