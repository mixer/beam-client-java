package com.mixer.api.futures.checkers;

import com.google.common.collect.ImmutableMap;
import com.mixer.api.exceptions.ForbiddenException;
import com.mixer.api.exceptions.MixerException;
import com.mixer.api.futures.SimpleFutureChecker;

public class JWT {
    private static final int UNAUTHORIZED_RESPONSE = 401;

    public static class JWTFutureChecker<T> extends SimpleFutureChecker<T, MixerException> {
        public JWTFutureChecker() {
            super(ImmutableMap.<Integer, Class<? extends MixerException>>of(
                    UNAUTHORIZED_RESPONSE, ForbiddenException.class
                )
            );
        }
    }
}
