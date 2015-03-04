package pro.beam.api.resource.chat.events;

import pro.beam.api.resource.chat.AbstractChatEvent;
import pro.beam.api.resource.chat.events.data.UserStatusData;

public class UserLeaveEvent extends AbstractChatEvent<UserStatusData> {
    public UserLeaveEvent() {
        this.event = EventType.USER_LEAVE;
    }
}
