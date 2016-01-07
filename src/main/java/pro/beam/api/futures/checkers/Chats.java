package pro.beam.api.futures.checkers;

import com.google.common.collect.ImmutableMap;
import pro.beam.api.exceptions.BadRequest;
import pro.beam.api.exceptions.BeamException;
import pro.beam.api.exceptions.Forbidden;
import pro.beam.api.exceptions.channel.ChannelNotFound;
import pro.beam.api.futures.SimpleFutureChecker;
import pro.beam.api.response.chat.ChatSettingsResponse;

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
