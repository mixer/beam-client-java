package com.mixer.api.futures.checkers;

import com.google.common.collect.ImmutableMap;
import com.mixer.api.exceptions.BeamException;
import com.mixer.api.exceptions.Forbidden;
import com.mixer.api.futures.SimpleFutureChecker;

public class JWT {
    private static final int UNAUTHORIZED_RESPONSE = 401;

    public static class JWTFutureChecker<T> extends SimpleFutureChecker<T, BeamException> {
        public JWTFutureChecker() {
            super(ImmutableMap.<Integer, Class<? extends BeamException>>of(
                    UNAUTHORIZED_RESPONSE, Forbidden.class
                )
            );
        }
    }
}
