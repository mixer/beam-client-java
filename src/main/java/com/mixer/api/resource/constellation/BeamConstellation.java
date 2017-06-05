package com.mixer.api.resource.constellation;

import com.mixer.api.BeamAPI;
import com.mixer.api.resource.constellation.ws.BeamConstellationConnectable;

public class BeamConstellation {
    public BeamConstellationConnectable connectable(BeamAPI beam) {
        return new BeamConstellationConnectable(beam, this);
    }
}
