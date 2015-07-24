package pro.beam.api.resource.chat.events.data;

import pro.beam.api.resource.chat.AbstractChatEvent;

import java.util.UUID;

public class DeleteMessageData extends AbstractChatEvent.EventData {
    public UUID id;
}
