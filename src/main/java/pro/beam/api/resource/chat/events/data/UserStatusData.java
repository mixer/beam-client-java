package pro.beam.api.resource.chat.events.data;

import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.chat.AbstractChatEvent;

import java.util.List;

public class UserStatusData extends AbstractChatEvent.EventData {
    public String username;
    public List<BeamUser.Role> roles;
    public int id;
}
