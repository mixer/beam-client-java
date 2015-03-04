package pro.beam.api.services.impl;

import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.resource.BeamChannel;
import pro.beam.api.services.AbstractHTTPService;

public class ChannelsService extends AbstractHTTPService {
    public ChannelsService(BeamAPI beam) {
        super(beam, "channels");
    }

    public ListenableFuture<BeamChannel> findOne(String id) {
        return this.get(id, BeamChannel.class);
    }
}

