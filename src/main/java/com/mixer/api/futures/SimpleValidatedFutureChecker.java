package com.mixer.api.futures;

import com.google.gson.Gson;
import com.mixer.api.exceptions.BeamException;
import com.mixer.api.exceptions.validation.ValidationError;
import com.mixer.api.http.HttpCompleteResponse;

import java.util.Map;

public class SimpleValidatedFutureChecker<V> extends AbstractFutureChecker<V, BeamException> {
    protected final SimpleFutureChecker<V, BeamException> simpleFutureChecker;
    protected final JojenFutureChecker<V> jojen;

    public SimpleValidatedFutureChecker(Gson gson, Map<Integer, Class<? extends BeamException>> exceptions) {
        this.simpleFutureChecker = new SimpleFutureChecker<>(exceptions);
        this.jojen = new JojenFutureChecker<>(gson);
    }

    @Override protected BeamException getException(HttpCompleteResponse response) {
        ValidationError validationError = this.jojen.getException(response);
        if (validationError != null) {
            return validationError;
        }

        return this.simpleFutureChecker.getException(response);
    }
}
