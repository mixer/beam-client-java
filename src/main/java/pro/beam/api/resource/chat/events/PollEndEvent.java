package pro.beam.api.resource.chat.events;

import pro.beam.api.resource.chat.events.data.PollEndData;

public class PollEndEvent extends GenericChatEvent<PollEndData> {
    public PollEndEvent() {
        this.event = EventType.POLL_END;
    }
}
