package pro.beam.api.resource.constellation.events;

import pro.beam.api.resource.constellation.AbstractConstellationEvent;
import pro.beam.api.resource.constellation.events.data.LiveData;

public class LiveEvent extends AbstractConstellationEvent<LiveData> {
    public LiveEvent() {
        this.type = Type.EVENT;
    }
}