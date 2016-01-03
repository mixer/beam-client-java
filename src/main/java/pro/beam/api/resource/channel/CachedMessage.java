package pro.beam.api.resource.channel;

import com.google.gson.annotations.SerializedName;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.chat.events.data.IncomingMessageData;
import pro.beam.api.resource.chat.events.data.MessageComponent;

import java.util.List;
import java.util.UUID;

public class CachedMessage {
    public MessageComponent message;
    public int channel;
    public UUID id;
    @SerializedName("user_id") public int userId;
    @SerializedName("user_name") public String userName;
    @SerializedName("user_roles") public List<BeamUser.Role> userRoles;

    public IncomingMessageData getMessage() {
        IncomingMessageData d = new IncomingMessageData();
        d.channel = this.channel;
        d.id = this.id.toString();
        d.userId = this.userId;
        d.userName = this.userName;
        d.message = this.message;
        d.userRoles = this.userRoles;

        return d;
    }
}
