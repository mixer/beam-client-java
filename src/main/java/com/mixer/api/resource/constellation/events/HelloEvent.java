package com.mixer.api.resource.constellation.events;

import com.mixer.api.resource.constellation.AbstractConstellationEvent;
import com.mixer.api.resource.constellation.events.data.HelloData;

public class HelloEvent extends AbstractConstellationEvent<HelloData> {
    public HelloEvent() {
        this.type = Type.EVENT;
    }
}
