package pro.beam.api.futures;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import pro.beam.api.exceptions.validation.ValidationError;
import pro.beam.api.http.HttpCompleteResponse;
import pro.beam.api.response.jojen.ValidationErrorResponse;

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

        if (r == null || r.isEmpty()) {
            return null;
        }

        return new ValidationError(r);
    }
}
