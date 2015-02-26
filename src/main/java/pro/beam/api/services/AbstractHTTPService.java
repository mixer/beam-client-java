package pro.beam.api.services;

import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.http.RequestType;

import java.io.IOException;

public abstract class AbstractHTTPService extends AbstractBeamService {
    public AbstractHTTPService(BeamAPI beam) {
        super(beam);
    }

    public <T> ListenableFuture<T> get(String path, Class<T> type) throws IOException {
        return this.beam.get(path, type);
    }

    public
}
