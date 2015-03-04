package pro.beam.api.resource.chat.events.data;

import pro.beam.api.resource.chat.AbstractChatEvent;

public class StatusData extends AbstractChatEvent.EventData {
    public int viewers;
    public int chatters;
}
