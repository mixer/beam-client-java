package com.mixer.api.services.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;
import com.mixer.api.BeamAPI;
import com.mixer.api.response.channels.ShowChannelsResponse;
import com.mixer.api.response.channels.ShowSlugsRepsonse;
import com.mixer.api.services.AbstractHTTPService;
import com.mixer.api.util.Enums;

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
        onlineRestriction.putParams(params);

        return this.get("", ShowSlugsRepsonse.class, params.build());
    }

    public ListenableFuture<ShowChannelsResponse> channels(int id) {
        return this.channels(id, 0, 50, null, null, ShowSlugsRepsonse.OnlineRestriction.NONE, null);
    }

    public ListenableFuture<ShowChannelsResponse> channels(int id,
                                                           int page, int limit,
                                                           ShowChannelsResponse.Attributes orderAttribute, ShowChannelsResponse.Ordering ordering,
                                                           ShowSlugsRepsonse.OnlineRestriction onlineRestriction,
                                                           ShowChannelsResponse.Attributes only) {

        ImmutableMap.Builder<String, Object> arguments = ImmutableMap.builder();

        if (orderAttribute != null) arguments.put("order", Enums.serializedName(orderAttribute)+":"+Enums.serializedName(ordering));
        if (only != null) arguments.put("only", Enums.serializedName(only));
        arguments.put("page", Math.max(0, page));
        arguments.put("limit", Math.min(50, limit));
        arguments.put("noCount", 1);
        onlineRestriction.putParams(arguments);

        return this.get(id+"/channels", ShowChannelsResponse.class, arguments.build());
    }
}
