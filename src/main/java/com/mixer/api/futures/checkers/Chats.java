package com.mixer.api.futures.checkers;

import com.google.common.collect.ImmutableMap;
import com.mixer.api.exceptions.BadRequest;
import com.mixer.api.exceptions.BeamException;
import com.mixer.api.exceptions.Forbidden;
import com.mixer.api.exceptions.channel.ChannelNotFound;
import com.mixer.api.futures.SimpleFutureChecker;
import com.mixer.api.response.chat.ChatSettingsResponse;

public class Chats {
    private static final int BAD_REQUEST_RESPONSE = 400;
    private static final int FORBIDDEN_RESPONSE = 403;
    private static final int CHANNEL_NOT_FOUND = 404;

    public static class UpdateSettingsChecker extends SimpleFutureChecker<ChatSettingsResponse, BeamException> {
        public UpdateSettingsChecker() {
            super(ImmutableMap.of(
                    BAD_REQUEST_RESPONSE, BadRequest.class,
                    FORBIDDEN_RESPONSE, Forbidden.class,
                    CHANNEL_NOT_FOUND, ChannelNotFound.class
                )
            );
        }
    }
}
