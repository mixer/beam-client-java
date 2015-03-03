package pro.beam.api.resource.chat.events;

import pro.beam.api.resource.chat.AbstractChatEvent;
import pro.beam.api.resource.chat.events.data.PollStartData;

public class PollStartEvent extends AbstractChatEvent<PollStartData> {
    public PollStartEvent() {
        this.event = EventType.POLL_START;
    }
}
