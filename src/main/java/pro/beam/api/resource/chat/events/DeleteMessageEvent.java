package pro.beam.api.resource.chat.events;

import pro.beam.api.resource.chat.AbstractChatEvent;
import pro.beam.api.resource.chat.events.data.DeleteMessageData;

public class DeleteMessageEvent extends AbstractChatEvent<DeleteMessageData> {
    public DeleteMessageEvent() {
        this.type = Type.EVENT;
    }
}
