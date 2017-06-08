package com.mixer.api.resource.constellation;

import com.mixer.api.MixerAPI;
import com.mixer.api.resource.constellation.ws.MixerConstellationConnectable;

public class MixerConstellation {
    public MixerConstellationConnectable connectable(MixerAPI mixer) {
        return new MixerConstellationConnectable(mixer, this);
    }
}
