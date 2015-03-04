package pro.beam.api.services;

import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.http.BeamHttpClient;

import java.util.Map;

public abstract class AbstractHTTPService extends AbstractBeamService {
    protected final BeamHttpClient http;
    protected final String path;

    public AbstractHTTPService(BeamAPI beam, String path) {
        super(beam);
        this.http = this.beam.http;
        this.path = path;
    }

    protected <T> ListenableFuture<T> get(String path, Class<T> type) {
        return this.get(path, type, null);
    }

    protected <T> ListenableFuture<T> get(String path, Class<T> type, Map<String, Object> parameters) {
        return this.http.get(this.path(path), type, parameters);
    }

    protected <T> ListenableFuture<T> post(String path, Class<T> type, Object... args) {
        return this.http.post(this.path(path), type, args);
    }

    protected <T> ListenableFuture<T> put(String path, Class<T> type, Object... args) {
        return this.http.put(this.path(path), type, args);
    }

    protected <T> ListenableFuture<T> delete(String path, Class<T> type, Object... args) {
        return this.http.delete(this.path(path), type, args);
    }

    public String path(String relative) {
        return BeamAPI.BASE_PATH.resolve(this.path + "/" + relative).toString();
    }
}
