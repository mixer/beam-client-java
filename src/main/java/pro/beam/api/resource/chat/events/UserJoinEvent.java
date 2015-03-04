package pro.beam.api.resource.chat.events;

import pro.beam.api.resource.chat.AbstractChatEvent;
import pro.beam.api.resource.chat.events.data.UserStatusData;

public class UserJoinEvent extends AbstractChatEvent<UserStatusData> {
    public UserJoinEvent() {
        this.event = EventType.USER_JOIN;
    }
}
