package com.mixer.api.resource.chat.events;

import com.mixer.api.resource.chat.AbstractChatEvent;

public interface EventHandler<T extends AbstractChatEvent> {
    void onEvent(T event);
}
