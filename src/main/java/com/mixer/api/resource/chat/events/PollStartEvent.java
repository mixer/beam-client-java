package com.mixer.api.resource.chat.events;

import com.mixer.api.resource.chat.AbstractChatEvent;
import com.mixer.api.resource.chat.events.data.PollStartData;

public class PollStartEvent extends AbstractChatEvent<PollStartData> {
    public PollStartEvent() {
        this.event = EventType.POLL_START;
    }
}
