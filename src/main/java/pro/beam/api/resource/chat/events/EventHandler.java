package pro.beam.api.resource.chat.events;

import pro.beam.api.resource.chat.AbstractChatEvent;

public interface EventHandler<T extends AbstractChatEvent> {
    void onEvent(T event);
}
