package pro.beam.api.resource.constellation.methods;

import pro.beam.api.resource.constellation.AbstractConstellationMethod;
import pro.beam.api.resource.constellation.methods.data.LiveRequestData;

public class LiveSubscribeMethod extends AbstractConstellationMethod {
    public LiveSubscribeMethod() {
        super("livesubscribe");
    }


    public LiveRequestData params;
}
