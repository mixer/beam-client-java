package pro.beam.api.resource.chat.events.data;

import pro.beam.api.resource.chat.events.GenericChatEvent;

import java.util.Map;

public class PollEndData extends GenericChatEvent.EventData {
    public int voters;
    public Map<String, Integer> responses;
}
