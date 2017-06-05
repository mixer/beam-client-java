package com.mixer.api.resource.chat.events.data;

import com.mixer.api.resource.chat.AbstractChatEvent;

import java.util.Map;

public class PollEndData extends AbstractChatEvent.EventData {
    public int voters;
    public Map<String, Integer> responses;
}
