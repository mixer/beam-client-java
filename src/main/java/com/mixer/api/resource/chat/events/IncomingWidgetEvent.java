package com.mixer.api.resource.chat.events;

import com.mixer.api.resource.chat.AbstractChatEvent;
import com.mixer.api.resource.chat.events.data.IncomingWidgetData;

public class IncomingWidgetEvent extends AbstractChatEvent<IncomingWidgetData> {
    public IncomingWidgetEvent() {
        this.event = EventType.WIDGET_MESSAGE;
    }
}
