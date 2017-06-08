package com.mixer.api.resource.constellation.events.data;

import com.google.gson.JsonObject;
import com.mixer.api.resource.constellation.AbstractConstellationEvent;

public class LiveData extends AbstractConstellationEvent.EventData {
    public String channel;
    public JsonObject payload;
}
