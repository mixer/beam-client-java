package pro.beam.api.resource.chat.events.data;

import java.util.List;

import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.chat.AbstractChatEvent;

public class IncomingWidgetData extends AbstractChatEvent.EventData {
    public int channel;
    public String id;
    public String user_name;
    public String user_id;
    public List<BeamUser.Role> user_roles;
    public String message;

    public String getMessage() {
        return message;
    }

}