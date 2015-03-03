package pro.beam.api.resource.chat.events.data;

import pro.beam.api.resource.chat.AbstractChatEvent;

import java.util.Map;

public class PollEndData extends AbstractChatEvent.EventData {
    public int voters;
    public Map<String, Integer> responses;
}
