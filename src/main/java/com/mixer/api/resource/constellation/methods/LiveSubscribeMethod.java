package com.mixer.api.resource.constellation.methods;

import com.mixer.api.resource.constellation.AbstractConstellationMethod;
import com.mixer.api.resource.constellation.methods.data.LiveRequestData;

public class LiveSubscribeMethod extends AbstractConstellationMethod {
    public LiveSubscribeMethod() {
        super("livesubscribe");
    }


    public LiveRequestData params;
}
