package com.mixer.api.futures.checkers;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.mixer.api.exceptions.MixerException;
import com.mixer.api.exceptions.channel.MissingPermissionException;
import com.mixer.api.futures.SimpleValidatedFutureChecker;
import com.mixer.api.response.channels.ChannelStatusResponse;

public class Channels {
    public static class StatusChecker extends SimpleValidatedFutureChecker<ChannelStatusResponse> {
        private static final int MISSING_PERMISSION_CODE = 403;

        public StatusChecker(Gson gson) {
            super(gson, ImmutableMap.<Integer, Class<? extends MixerException>>of(
                MISSING_PERMISSION_CODE, MissingPermissionException.class
            ));
        }
    }
}
