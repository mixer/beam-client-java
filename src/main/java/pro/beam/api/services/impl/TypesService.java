package pro.beam.api.services.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.response.channels.ShowChannelsResponse;
import pro.beam.api.response.channels.ShowSlugsRepsonse;
import pro.beam.api.services.AbstractHTTPService;
import pro.beam.api.util.Enums;

import java.util.Map;

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



    public ListenableFuture<ShowChannelsResponse> channels(int id) {
        return this.channels(id, 0, 50, null, null, null);
    }

    public ListenableFuture<ShowChannelsResponse> channels(int id,
                                                           int page, int limit,
                                                           ShowChannelsResponse.Attributes orderAttribute, ShowChannelsResponse.Ordering ordering,
                                                           ShowChannelsResponse.Attributes only) {

        ImmutableMap.Builder<String, Object> arguments = ImmutableMap.builder();

        if (orderAttribute != null) arguments.put("order", Enums.serializedName(orderAttribute)+":"+Enums.serializedName(ordering));
        if (only != null) arguments.put("only", Enums.serializedName(only));
        arguments.put("page", Math.max(0, page));
        arguments.put("limit", Math.min(50, limit));

        return this.get(id+"/channels", ShowChannelsResponse.class, arguments.build());
    }
}
