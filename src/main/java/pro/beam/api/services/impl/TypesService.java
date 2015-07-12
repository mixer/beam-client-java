package pro.beam.api.services.impl;

import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.response.channels.ShowSlugsRepsonse;
import pro.beam.api.services.AbstractHTTPService;

public class TypesService extends AbstractHTTPService {
    public TypesService(BeamAPI beam) {
        super(beam, "types");
    }

    public ListenableFuture<ShowSlugsRepsonse> all() {
        return this.get("", ShowSlugsRepsonse.class);
    }
}
