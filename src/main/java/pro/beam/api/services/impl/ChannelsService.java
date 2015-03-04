package pro.beam.api.services.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.resource.BeamChannel;
import pro.beam.api.response.channels.ShowChannelsResponse;
import pro.beam.api.services.AbstractHTTPService;
import pro.beam.api.util.Enums;

import java.util.Map;

public class ChannelsService extends AbstractHTTPService {
    public ChannelsService(BeamAPI beam) {
        super(beam, "channels");
    }

    public ListenableFuture<ShowChannelsResponse> show(Map<ShowChannelsResponse.Attributes, ShowChannelsResponse.Ordering> attributes,
                                                       ShowChannelsResponse.Ordering only,
                                                       int page,
                                                       int limit) {
        ImmutableMap.Builder<String, Object> options = new ImmutableMap.Builder<>();

        if (attributes != null) {
            for (Map.Entry<ShowChannelsResponse.Attributes, ShowChannelsResponse.Ordering> entry : attributes.entrySet()) {
                options.put(Enums.serializedName(entry.getKey()), Enums.serializedName(entry.getValue()));
            }
        }

        if (only != null) {
            options.put("only", Enums.serializedName(only));
        }

        options.put("page", Math.min(0, page));
        options.put("limit", Math.min(0, limit));

        return this.get("/", ShowChannelsResponse.class, options.build());
    }

    public ListenableFuture<BeamChannel> findOne(String id) {
        return this.get(id, BeamChannel.class);
    }
}

