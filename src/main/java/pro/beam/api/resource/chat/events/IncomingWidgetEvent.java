package pro.beam.api.resource.chat.events;

import pro.beam.api.resource.chat.AbstractChatEvent;
import pro.beam.api.resource.chat.events.data.IncomingWidgetData;

public class IncomingWidgetEvent extends AbstractChatEvent<IncomingWidgetData> {
    public IncomingWidgetEvent() {
        this.event = EventType.WIDGET_MESSAGE;
    }
}
