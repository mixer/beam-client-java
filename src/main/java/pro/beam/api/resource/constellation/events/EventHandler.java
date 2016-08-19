package pro.beam.api.resource.constellation.events;

import pro.beam.api.resource.constellation.AbstractConstellationEvent;

public interface EventHandler<T extends AbstractConstellationEvent> {
    void onEvent(T event);
}
