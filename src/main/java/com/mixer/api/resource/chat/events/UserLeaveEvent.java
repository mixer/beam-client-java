package com.mixer.api.resource.chat.events;

import com.mixer.api.resource.chat.AbstractChatEvent;
import com.mixer.api.resource.chat.events.data.UserStatusData;

public class UserLeaveEvent extends AbstractChatEvent<UserStatusData> {
    public UserLeaveEvent() {
        this.event = EventType.USER_LEAVE;
    }
}
