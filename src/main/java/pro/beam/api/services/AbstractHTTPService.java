package pro.beam.api.services;

import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.http.BeamHttpClient;

public abstract class AbstractHTTPService extends AbstractBeamService {
    protected final BeamHttpClient http;

    public AbstractHTTPService(BeamAPI beam) {
        super(beam);
        this.http = this.beam.http();
    }

    public <T> ListenableFuture<T> get(String path, Class<T> type) {
        return this.http.get(path, type);
    }

    public <T> ListenableFuture<T> post(String path, Class<T> type, Object... args) {
        return this.http.post(path, type, args);
    }

    public <T> ListenableFuture<T> put(String path, Class<T> type, Object... args) {
        return this.http.put(path, type, args);
    }

    public <T> ListenableFuture<T> delete(String path, Class<T> type) {
        return this.http.delete(path, type);
    }
}
