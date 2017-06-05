package com.mixer.api.resource.chat.events.data;

import com.mixer.api.resource.chat.AbstractChatEvent;

public class StatusData extends AbstractChatEvent.EventData {
    public int viewers;
    public int chatters;
}
