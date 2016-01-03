package pro.beam.api.futures;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class SimpleFutureChecker<V, E extends Exception> extends AbstractFutureChecker<V, E> {
    protected final ImmutableMap<Integer, Class<? extends E>> exceptions;
    public SimpleFutureChecker(Map<Integer, Class<? extends E>> exceptions) {
        this.exceptions = ImmutableMap.copyOf(exceptions);
    }

    @Override
    protected E getException(int statusCode) {
        try {
            return this.exceptions.get(statusCode).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
