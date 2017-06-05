package com.mixer.api.resource.chat.events;

import com.mixer.api.resource.chat.AbstractChatEvent;
import com.mixer.api.resource.chat.events.data.PollEndData;

public class PollEndEvent extends AbstractChatEvent<PollEndData> {
    public PollEndEvent() {
        this.event = EventType.POLL_END;
    }
}
