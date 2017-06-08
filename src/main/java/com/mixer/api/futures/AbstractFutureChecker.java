package com.mixer.api.futures;

import com.google.common.base.Function;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.mixer.api.http.HttpBadResponseException;
import com.mixer.api.http.HttpCompleteResponse;

public abstract class AbstractFutureChecker<V, E extends Exception> {
    public CheckedFuture<V, E> check(ListenableFuture<V> future) {
        return Futures.makeChecked(future, new Function<Exception, E>() {
            @Override public E apply(Exception e) {
                Throwable cause = e.getCause();
                if (!(cause instanceof HttpBadResponseException)) {
                    cause.printStackTrace(); // TODO
                    return null;
                }

                HttpBadResponseException hbre = (HttpBadResponseException) cause;
                return AbstractFutureChecker.this.getException(hbre.response);
            }
        });
    }

    protected abstract E getException(HttpCompleteResponse response);
}
