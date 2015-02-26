package pro.beam.api.util;

import com.google.common.util.concurrent.FutureCallback;

/**
 * This class provides an implementation of the #onFailure method of the FutureCallback
 * class, yet leaves the implementation of the #onSuccess method to the implementer.
 *
 * @param <T> The type of data to expect in a successful response.
 */
public abstract class ResponseHandler<T> implements FutureCallback<T> {
    @Override
    public void onFailure(Throwable throwable) {
        throwable.printStackTrace();
    }
}
