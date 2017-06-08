package com.mixer.api.resource.chat.events;

import com.mixer.api.resource.chat.AbstractChatEvent;
import com.mixer.api.resource.chat.events.data.IncomingMessageData;

public class IncomingMessageEvent extends AbstractChatEvent<IncomingMessageData> {
    public IncomingMessageEvent() {
        this.event = EventType.CHAT_MESSAGE;
    }
}
