package pro.beam.api.resource.channel;

import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.chat.events.data.IncomingMessageData;

import java.util.List;
import java.util.UUID;

public class CachedMessage {
    public List<IncomingMessageData.MessagePart> message;
    public int channel;
    public UUID id;
    public int user_id;
    public String user_name;
    public List<BeamUser.Role> roles;

    public IncomingMessageData getMessage() {
        IncomingMessageData d = new IncomingMessageData();
        d.channel = this.channel;
        d.id = this.id.toString();
        d.user_id = String.valueOf(this.user_id);
        d.user_name = this.user_name;
        d.message = this.message;

        return d;
    }
}
