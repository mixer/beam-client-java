package com.mixer.api.resource.chat.events;

import com.mixer.api.resource.chat.AbstractChatEvent;
import com.mixer.api.resource.chat.events.data.DeleteMessageData;

public class DeleteMessageEvent extends AbstractChatEvent<DeleteMessageData> {
    public DeleteMessageEvent() {
        this.event = EventType.DELETE_MESSAGE;
    }
}
