package pro.beam.api.futures.checkers;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import pro.beam.api.exceptions.BeamException;
import pro.beam.api.exceptions.channel.MissingPermissionException;
import pro.beam.api.futures.SimpleValidatedFutureChecker;
import pro.beam.api.response.channels.ChannelStatusResponse;

public class Channels {
    public static class StatusChecker extends SimpleValidatedFutureChecker<ChannelStatusResponse> {
        private static final int MISSING_PERMISSION_CODE = 403;

        public StatusChecker(Gson gson) {
            super(gson, ImmutableMap.<Integer, Class<? extends BeamException>>of(
                MISSING_PERMISSION_CODE, MissingPermissionException.class
            ));
        }
    }
}
