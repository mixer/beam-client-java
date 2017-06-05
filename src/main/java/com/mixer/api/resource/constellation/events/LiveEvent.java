package com.mixer.api.resource.constellation.events;

import com.mixer.api.resource.constellation.AbstractConstellationEvent;
import com.mixer.api.resource.constellation.events.data.LiveData;

public class LiveEvent extends AbstractConstellationEvent<LiveData> {
    public LiveEvent() {
        this.type = Type.EVENT;
    }
}