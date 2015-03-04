package pro.beam.api.resource.chat.events;

import pro.beam.api.resource.chat.AbstractChatEvent;
import pro.beam.api.resource.chat.events.data.StatusData;

public class StatusEvent extends AbstractChatEvent<StatusData> {
    public StatusEvent() {
        this.event = EventType.STATS;
    }
}
