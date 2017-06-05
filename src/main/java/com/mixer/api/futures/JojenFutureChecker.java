package com.mixer.api.futures;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mixer.api.exceptions.validation.ValidationError;
import com.mixer.api.http.HttpCompleteResponse;
import com.mixer.api.response.jojen.ValidationErrorResponse;

public class JojenFutureChecker<V> extends AbstractFutureChecker<V, ValidationError> {
    protected final Gson gson;
    public JojenFutureChecker(Gson gson) {
        this.gson = gson;
    }

    @Override protected ValidationError getException(HttpCompleteResponse response) {
        ValidationErrorResponse r;
        try {
            r = this.gson.fromJson(response.body(), ValidationErrorResponse.class);
        } catch (JsonSyntaxException e) {
            return null;
        }

        if (r == null || r.details.isEmpty()) {
            return null;
        }

        return new ValidationError(r.details);
    }
}
