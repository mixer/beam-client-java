package pro.beam.api.futures;

import com.google.common.base.Function;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.apache.http.client.HttpResponseException;

public abstract class AbstractFutureChecker<V, E extends Exception> {
    public CheckedFuture<V, E> check(ListenableFuture<V> future) {
        return Futures.makeChecked(future, new Function<Exception, E>() {
            @Override public E apply(Exception e) {
                Throwable cause = e.getCause();
                if (!(cause instanceof HttpResponseException)) {
                    cause.printStackTrace(); // TODO
                    return null;
                }

                HttpResponseException hre = (HttpResponseException) cause;
                return AbstractFutureChecker.this.getException(hre.getStatusCode());
            }
        });
    }

    protected abstract E getException(int statusCode);
}
