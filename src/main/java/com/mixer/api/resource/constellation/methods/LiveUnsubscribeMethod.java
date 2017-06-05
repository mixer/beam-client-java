package com.mixer.api.resource.constellation.methods;

import com.mixer.api.resource.constellation.AbstractConstellationMethod;
import com.mixer.api.resource.constellation.methods.data.LiveRequestData;

public class LiveUnsubscribeMethod extends AbstractConstellationMethod {
    public LiveUnsubscribeMethod() {
        super("liveunsubscribe");
    }


    public LiveRequestData params;
}
