package com.mixer.api.resource.chat.events;

import com.mixer.api.resource.chat.AbstractChatEvent;
import com.mixer.api.resource.chat.events.data.StatusData;

public class StatusEvent extends AbstractChatEvent<StatusData> {
    public StatusEvent() {
        this.event = EventType.STATS;
    }
}
