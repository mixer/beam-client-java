package pro.beam.api.resource.chat.events;

import pro.beam.api.resource.chat.AbstractChatEvent;
import pro.beam.api.resource.chat.events.data.PollEndData;

public class PollEndEvent extends AbstractChatEvent<PollEndData> {
    public PollEndEvent() {
        this.event = EventType.POLL_END;
    }
}
