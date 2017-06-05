package com.mixer.api.resource.chat.events;

import com.mixer.api.resource.chat.AbstractChatEvent;
import com.mixer.api.resource.chat.events.data.UserStatusData;

public class UserJoinEvent extends AbstractChatEvent<UserStatusData> {
    public UserJoinEvent() {
        this.event = EventType.USER_JOIN;
    }
}
