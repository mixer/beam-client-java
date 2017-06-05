package com.mixer.api.services.impl;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.mixer.api.MixerAPI;
import com.mixer.api.exceptions.MixerException;
import com.mixer.api.futures.checkers.JWT;
import com.mixer.api.services.AbstractHTTPService;

/**
 * Implements a JWT service.
 */
public class JWTService extends AbstractHTTPService {
    public JWTService(MixerAPI mixer) {
        super(mixer, "jwt");
    }

    /**
     * Retrieve a JWT from the api. When passed in a MixerUser use this as the result.
     * @return
     */
    public <T> CheckedFuture<T, MixerException> authorize(final T value) {
        return new JWT.JWTFutureChecker<T>().check(Futures.transform(
            this.post("authorize", null, new Object()),
            new AsyncFunction<Object, T>() {
                @Override
                public ListenableFuture<T> apply(Object o) throws Exception {
                    return Futures.immediateCheckedFuture(value);
                }
            }
        ));
    }
}
