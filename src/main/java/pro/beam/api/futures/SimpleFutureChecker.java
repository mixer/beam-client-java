package pro.beam.api.futures;

import com.google.common.collect.ImmutableMap;
import pro.beam.api.http.HttpCompleteResponse;

import java.util.Map;

public class SimpleFutureChecker<V, E extends Exception> extends AbstractFutureChecker<V, E> {
    protected final ImmutableMap<Integer, Class<? extends E>> exceptions;
    public SimpleFutureChecker(Map<Integer, Class<? extends E>> exceptions) {
        this.exceptions = ImmutableMap.copyOf(exceptions);
    }

    @Override
    protected E getException(HttpCompleteResponse response) {
        try {
            return this.exceptions.get(response.status()).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
