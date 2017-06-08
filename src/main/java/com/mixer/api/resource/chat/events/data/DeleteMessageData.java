package com.mixer.api.resource.chat.events.data;

import com.mixer.api.resource.chat.AbstractChatEvent;

import java.util.UUID;

public class DeleteMessageData extends AbstractChatEvent.EventData {
    public UUID id;
}
