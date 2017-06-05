package com.mixer.api.services.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.mixer.api.BeamAPI;
import com.mixer.api.futures.checkers.Chats;
import com.mixer.api.http.BeamHttpClient;
import com.mixer.api.resource.channel.BeamChannel;
import com.mixer.api.resource.chat.BeamChat;
import com.mixer.api.response.chat.ChatSettingsResponse;
import com.mixer.api.response.chat.MessagesResponse;
import com.mixer.api.response.chat.OnlineUsersResponse;
import com.mixer.api.services.AbstractHTTPService;

public class ChatService extends AbstractHTTPService {
    public ChatService(BeamAPI beam) {
        super(beam, "chats");
    }

    @Deprecated
    public ListenableFuture<MessagesResponse> messages(BeamChannel channel, int start, int end, int limit) {
        return this.get(String.format("%d/message", channel.id), MessagesResponse.class, BeamHttpClient.getArgumentsBuilder()
                .put("id", channel.id)
                .put("start", start)
                .put("end", end)
                .put("limit", limit)
                .build());
    }

    public ListenableFuture<OnlineUsersResponse> users(BeamChannel channel, int page, int limit) {
        return this.get(
                String.format("%d/users", channel.id),
                OnlineUsersResponse.class,
                BeamHttpClient.getArgumentsBuilder()
                    .put("id", channel.id)
                    .put("page", page)
                    .put("limit", limit)
                    .put("noCount", 1)
                .build()
        );
    }

    public ListenableFuture<OnlineUsersResponse> usersSearch(String username, BeamChannel channel, int page, int limit) {
        return this.get(
                String.format("%d/users/search", channel.id),
                OnlineUsersResponse.class,
                BeamHttpClient.getArgumentsBuilder()
                    .put("page", page)
                    .put("limit", limit)
                    .put("id", channel.id)
                    .put("username", username)
                    .put("noCount", 1)
                .build()
        );
    }

    public ListenableFuture<ChatSettingsResponse> updateSettings(BeamChannel channel, boolean linksAllowed, boolean linksClickable, int slowchat) {
        return new Chats.UpdateSettingsChecker().check(this.get(
                String.format("%d", channel.id),
                ChatSettingsResponse.class,
                BeamHttpClient.getArgumentsBuilder()
                    .put("id", channel.id)
                    .put("linksAllowed", linksAllowed)
                    .put("linksClickable", linksClickable)
                    .put("slowchat", slowchat)
                .build()
        ));
    }

    public ListenableFuture<BeamChat> findOne(int id) {
        return this.get(String.valueOf(id), BeamChat.class);
    }
}
