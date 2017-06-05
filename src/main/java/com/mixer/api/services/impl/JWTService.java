package com.mixer.api.services.impl;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.mixer.api.BeamAPI;
import com.mixer.api.exceptions.BeamException;
import com.mixer.api.futures.checkers.JWT;
import com.mixer.api.services.AbstractHTTPService;

/**
 * Implements a JWT service.
 */
public class JWTService extends AbstractHTTPService {
    public JWTService(BeamAPI beam) {
        super(beam, "jwt");
    }

    /**
     * Retrieve a JWT from the api. When passed in a BeamUser use this as the result.
     * @return
     */
    public <T> CheckedFuture<T, BeamException> authorize(final T value) {
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
