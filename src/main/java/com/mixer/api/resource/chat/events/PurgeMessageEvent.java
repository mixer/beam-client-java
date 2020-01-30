package com.mixer.api.resource.chat.events;

import com.mixer.api.resource.chat.AbstractChatEvent;
import com.mixer.api.resource.chat.events.data.PurgeMessageData;

public class PurgeMessageEvent extends AbstractChatEvent<PurgeMessageData> {
    public PurgeMessageEvent() {
        this.event = EventType.PURGE_MESSAGE;
    }
}
