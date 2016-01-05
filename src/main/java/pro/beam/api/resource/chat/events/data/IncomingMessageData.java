package pro.beam.api.resource.chat.events.data;

import java.util.List;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.chat.AbstractChatEvent;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.gson.annotations.SerializedName;
import pro.beam.api.resource.chat.events.data.MessageComponent.MessageTextComponent;

public class IncomingMessageData extends AbstractChatEvent.EventData {
    public int channel;
    public String id;
    @SerializedName("user_name") public String userName;
    @SerializedName("user_id") public int userId;
    @SerializedName("user_roles") public List<BeamUser.Role> userRoles;
    public MessageComponent message;

    @Deprecated()
    public String getMessage() {
        return this.asString();
    }

    public String asString() {
        Joiner j = Joiner.on("");
        Iterable<MessageTextComponent> components = Collections2.filter(this.message.message, Predicates.notNull());

        return j.join(Iterables.transform(components, new Function<MessageTextComponent, String>() {
            @Override public String apply(MessageTextComponent component) {
                switch (component.type) {
                    case EMOTICON: return component.text;
                    case LINK: return component.url;
                    case TEXT: default: return component.data;
                }
            }
        }));
    }
}
