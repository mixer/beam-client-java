package pro.beam.api.resource.chat.events;

import pro.beam.api.resource.chat.events.data.IncomingMessageData;

public class IncomingMessageEvent extends GenericChatEvent<IncomingMessageData> {
    public IncomingMessageEvent() {
        this.event = EventType.CHAT_MESSAGE;
    }
}
