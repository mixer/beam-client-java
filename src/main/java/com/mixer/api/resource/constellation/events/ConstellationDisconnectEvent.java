package com.mixer.api.resource.constellation.events;

import com.mixer.api.resource.constellation.AbstractConstellationEvent;
import com.mixer.api.resource.constellation.events.data.ConstellationDisconnectData;

public class ConstellationDisconnectEvent extends AbstractConstellationEvent<ConstellationDisconnectData> {
    public ConstellationDisconnectEvent() {
        this.type = Type.EVENT;
    }
}
