package pro.beam.api.resource.chat.events.data;

import java.util.List;

import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.chat.AbstractChatEvent;

public class IncomingWidgetData extends AbstractChatEvent.EventData {
    public int channel;
    public String id;
    public String userName;
    public String userId;
    public List<BeamUser.Role> userRoles;
    public String message;

    public String getMessage() {
        return message;
    }

}