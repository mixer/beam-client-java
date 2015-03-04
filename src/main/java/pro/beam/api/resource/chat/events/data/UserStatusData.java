package pro.beam.api.resource.chat.events.data;

import pro.beam.api.resource.chat.AbstractChatEvent;

public class UserStatusData extends AbstractChatEvent.EventData {
    public String username;
    public String role;
    public int id;
}
