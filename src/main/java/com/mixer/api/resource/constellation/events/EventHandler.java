package com.mixer.api.resource.constellation.events;

import com.mixer.api.resource.constellation.AbstractConstellationEvent;

public interface EventHandler<T extends AbstractConstellationEvent> {
    void onEvent(T event);
}
