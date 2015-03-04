package pro.beam.api.services.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.http.BeamHttpClient;
import pro.beam.api.resource.channel.BeamChannel;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.response.channels.ShowChannelsResponse;
import pro.beam.api.response.channels.ShowSlugsRepsonse;
import pro.beam.api.services.AbstractHTTPService;
import pro.beam.api.util.Enums;

import java.lang.reflect.Field;
import java.util.Map;

public class ChannelsService extends AbstractHTTPService {
    public ChannelsService(BeamAPI beam) {
        super(beam, "channels");
    }

    public ListenableFuture<ShowChannelsResponse> show(Map<ShowChannelsResponse.Attributes, ShowChannelsResponse.Ordering> attributes,
                                                       ShowChannelsResponse.Ordering only,
                                                       int page,
                                                       int limit) {
        ImmutableMap.Builder<String, Object> options = BeamHttpClient.getArgumentsBuilder();

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

    public void follow(BeamChannel channel, BeamUser follower) {
        ImmutableMap.Builder<String, Object> arguments = BeamHttpClient.getArgumentsBuilder();
        arguments.put("user", follower.id);

        this.put(channel.id + "/follow", null, arguments.build());
    }

    public ListenableFuture<ShowChannelsResponse> search(ShowChannelsResponse.Scope scope,
                                                         String query,
                                                         int page, int limit) {
        ImmutableMap.Builder<String, Object> options = BeamHttpClient.getArgumentsBuilder();
        options.put(Enums.serializedName(scope), query);

        options.put("page", Math.min(0, page));
        options.put("limit", Math.min(0, limit));

        return this.get("search", ShowChannelsResponse.class, options.build());
    }

    public ListenableFuture<ShowSlugsRepsonse> showTypes() {
        return this.get("types", ShowSlugsRepsonse.class);
    }


    public void unfollow(BeamChannel channel, BeamUser exFollower) {
        ImmutableMap.Builder<String, Object> arguments = BeamHttpClient.getArgumentsBuilder();
        arguments.put("user", exFollower.id);

        this.delete(channel.id + "/follow", null, arguments.build());
    }

    public ListenableFuture<BeamChannel> update(BeamChannel channel) {
        ImmutableMap.Builder<String, Object> arguments = BeamHttpClient.getArgumentsBuilder();
        for (Field f : channel.getClass().getFields()) {
            try {
                arguments.put(f.getName(), f.get(channel));
            } catch (IllegalAccessException ignored) { }
        }

        return this.put(String.valueOf(channel.id), BeamChannel.class, arguments.build());
    }
}
