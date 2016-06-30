package pro.beam.api.services.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.exceptions.BeamException;
import pro.beam.api.futures.checkers.Channels;
import pro.beam.api.http.BeamHttpClient;
import pro.beam.api.http.SortOrderMap;
import pro.beam.api.resource.channel.BeamChannel;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.response.channels.ChannelStatusResponse;
import pro.beam.api.response.channels.ShowChannelsResponse;
import pro.beam.api.response.emotes.ChannelEmotesResponse;
import pro.beam.api.services.AbstractHTTPService;
import pro.beam.api.util.Enums;

import java.lang.reflect.Field;
import java.util.Map;

public class ChannelsService extends AbstractHTTPService {
    private static final String CHANNEL_ROOT = "";
    public ChannelsService(BeamAPI beam) {
        super(beam, "channels");
    }

    public ListenableFuture<ShowChannelsResponse> show(SortOrderMap<ShowChannelsResponse.Attributes, ShowChannelsResponse.Ordering> ordering,
                                                       int page,
                                                       int limit) {
        ImmutableMap.Builder<String, Object> options = BeamHttpClient.getArgumentsBuilder();

        if (ordering != null) {
            options.put("order", ordering.build());
        }

        options.put("page", Math.max(0, page));
        options.put("limit", Math.max(0, limit));

        return this.get("", ShowChannelsResponse.class, options.build());
    }

    /**
     * Finds a single BeamChannel by searching its token.
     *
     * @param token The token of the channel to return. Example: "ttaylorr".
     * @return A BeamChannel, if found, or null.
     */
    public ListenableFuture<BeamChannel> findOneByToken(String token) {
        return this.get(token, BeamChannel.class);
    }

    public ListenableFuture<BeamChannel> findOne(int id) {
        return this.get(String.valueOf(id), BeamChannel.class);
    }

    public ListenableFuture<BeamChannel> findOneByTokenDetailed(String token) {
        return this.get(String.format("%s/detailed", token), BeamChannel.class);
    }

    public ListenableFuture<BeamChannel> findOneDetailed(int id) {
        return this.get(String.format("%d/detailed", id), BeamChannel.class);
    }

    public CheckedFuture<ChannelStatusResponse, BeamException> findRelationship(BeamChannel channel, BeamUser user) {
        return new Channels.StatusChecker(this.beam.gson).check(this.get(
                String.format("%d/relationship", channel.id),
                ChannelStatusResponse.class,
                BeamHttpClient.getArgumentsBuilder()
                        .put("user", String.valueOf(user.id))
                    .build()
        ));
    }

    public ListenableFuture<?> follow(BeamChannel channel, BeamUser follower) {
        ImmutableMap.Builder<String, Object> arguments = BeamHttpClient.getArgumentsBuilder();
        arguments.put("user", follower.id);

        return this.put(channel.id + "/follow", null, arguments.build());
    }

    public ListenableFuture<?> unfollow(BeamChannel channel, BeamUser exFollower) {
        ImmutableMap.Builder<String, Object> arguments = BeamHttpClient.getArgumentsBuilder();
        arguments.put("user", exFollower.id);

        return this.delete(channel.id + "/follow", null, arguments.build());
    }

    public ListenableFuture<ShowChannelsResponse> search(String query,
                                                         ShowChannelsResponse.Scope scope,
                                                         int page, int limit) {
        ImmutableMap.Builder<String, Object> options = BeamHttpClient.getArgumentsBuilder();

        options.put("q", query);
        options.put("scope", Enums.serializedName(scope));
        options.put("page", Math.min(0, page));
        options.put("limit", Math.min(0, limit));

        return this.get(CHANNEL_ROOT, ShowChannelsResponse.class, options.build());
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

    public ListenableFuture<ChannelEmotesResponse> emotes(BeamChannel channel) {
        return this.get(String.format("%d/emoticons", channel.id), ChannelEmotesResponse.class);
    }
}
