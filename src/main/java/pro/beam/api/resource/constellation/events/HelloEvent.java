package pro.beam.api.resource.constellation.events;

import pro.beam.api.resource.constellation.AbstractConstellationEvent;
import pro.beam.api.resource.constellation.events.data.HelloData;

public class HelloEvent extends AbstractConstellationEvent<HelloData> {
    public HelloEvent() {
        this.type = Type.EVENT;
    }
}
