package pro.beam.api.services.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.response.channels.ShowSlugsRepsonse;
import pro.beam.api.services.AbstractHTTPService;

public class TypesService extends AbstractHTTPService {
    public TypesService(BeamAPI beam) {
        super(beam, "types");
    }

    public ListenableFuture<ShowSlugsRepsonse> all() {
        return this.all(null);
    }

    public ListenableFuture<ShowSlugsRepsonse> all(ShowSlugsRepsonse.OnlineRestriction onlineRestriction) {
        if (onlineRestriction == null) onlineRestriction = ShowSlugsRepsonse.OnlineRestriction.NONE;

        ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
        switch (onlineRestriction) {
            case ONLINE:
                params.put("where", "online.eq.1");
                break;
            case OFFLINE:
                params.put("where", "online.eq.0");
                break;
            case NONE:
                break;
        }

        return this.get("", ShowSlugsRepsonse.class, params.build());
    }
}
