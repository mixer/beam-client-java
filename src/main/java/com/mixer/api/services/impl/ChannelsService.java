package com.mixer.api.services.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.mixer.api.MixerAPI;
import com.mixer.api.exceptions.MixerException;
import com.mixer.api.futures.checkers.Channels;
import com.mixer.api.http.MixerHttpClient;
import com.mixer.api.http.SortOrderMap;
import com.mixer.api.resource.MixerUser;
import com.mixer.api.resource.channel.MixerChannel;
import com.mixer.api.response.channels.ChannelStatusResponse;
import com.mixer.api.response.channels.ShowChannelsResponse;
import com.mixer.api.response.emotes.ChannelEmotesResponse;
import com.mixer.api.response.users.UserFollowsResponse;
import com.mixer.api.services.AbstractHTTPService;
import com.mixer.api.util.Enums;

import java.lang.reflect.Field;

public class ChannelsService extends AbstractHTTPService {
    private static final String CHANNEL_ROOT = "";
    public ChannelsService(MixerAPI mixer) {
        super(mixer, "channels");
    }

    public ListenableFuture<ShowChannelsResponse> show(SortOrderMap<ShowChannelsResponse.Attributes, ShowChannelsResponse.Ordering> ordering,
                                                       int page,
                                                       int limit) {
        ImmutableMap.Builder<String, Object> options = MixerHttpClient.getArgumentsBuilder();

        if (ordering != null) {
            options.put("order", ordering.build());
        }

        options.put("page", Math.max(0, page));
        options.put("limit", Math.max(0, limit));
        options.put("noCount", 1);

        return this.get("", ShowChannelsResponse.class, options.build());
    }

    /**
     * Finds a single MixerChannel by searching its token.
     *
     * @param token The token of the channel to return. Example: "ttaylorr".
     * @return A MixerChannel, if found, or null.
     */
    public ListenableFuture<MixerChannel> findOneByToken(String token) {
        return this.get(token, MixerChannel.class);
    }

    public ListenableFuture<MixerChannel> findOne(int id) {
        return this.get(String.valueOf(id), MixerChannel.class);
    }

    public ListenableFuture<MixerChannel> findOneByTokenDetailed(String token) {
        return this.get(String.format("%s/detailed", token), MixerChannel.class);
    }

    public ListenableFuture<MixerChannel> findOneDetailed(int id) {
        return this.get(String.format("%d/detailed", id), MixerChannel.class);
    }

    public CheckedFuture<ChannelStatusResponse, MixerException> findRelationship(MixerChannel channel, MixerUser user) {
        return new Channels.StatusChecker(this.mixer.gson).check(this.get(
                String.format("%d/relationship", channel.id),
                ChannelStatusResponse.class,
                MixerHttpClient.getArgumentsBuilder()
                        .put("user", String.valueOf(user.id))
                    .build()
        ));
    }

    public ListenableFuture<UserFollowsResponse> follow(MixerChannel channel, MixerUser follower) {
        ImmutableMap.Builder<String, Object> arguments = MixerHttpClient.getArgumentsBuilder();
        arguments.put("user", follower.id);

        return this.put(channel.id + "/follow", null, arguments.build());
    }

    public ListenableFuture<UserFollowsResponse> unfollow(MixerChannel channel, MixerUser exFollower) {
        ImmutableMap.Builder<String, Object> arguments = MixerHttpClient.getArgumentsBuilder();
        arguments.put("user", exFollower.id);

        return this.delete(channel.id + "/follow", null, arguments.build());
    }

    public ListenableFuture<ShowChannelsResponse> search(String query,
                                                         ShowChannelsResponse.Scope scope,
                                                         int page, int limit) {
        ImmutableMap.Builder<String, Object> options = MixerHttpClient.getArgumentsBuilder();

        options.put("q", query);
        options.put("scope", Enums.serializedName(scope));
        options.put("page", Math.min(0, page));
        options.put("limit", Math.min(0, limit));
        options.put("noCount", 1);

        return this.get(CHANNEL_ROOT, ShowChannelsResponse.class, options.build());
    }

    public ListenableFuture<MixerChannel> update(MixerChannel channel) {
        ImmutableMap.Builder<String, Object> arguments = MixerHttpClient.getArgumentsBuilder();
        for (Field f : channel.getClass().getFields()) {
            try {
                arguments.put(f.getName(), f.get(channel));
            } catch (IllegalAccessException ignored) { }
        }

        return this.put(String.valueOf(channel.id), MixerChannel.class, arguments.build());
    }

    public ListenableFuture<ChannelEmotesResponse> emotes(MixerChannel channel) {
        return this.get(String.format("%d/emoticons", channel.id), ChannelEmotesResponse.class);
    }
}
