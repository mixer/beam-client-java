package pro.beam.api.resource.constellation.methods;

import pro.beam.api.resource.constellation.AbstractConstellationMethod;
import pro.beam.api.resource.constellation.methods.data.LiveRequestData;

public class LiveUnsubscribeMethod extends AbstractConstellationMethod {
    public LiveUnsubscribeMethod() {
        super("liveunsubscribe");
    }


    public LiveRequestData params;
}
