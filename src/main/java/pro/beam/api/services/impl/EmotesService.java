package pro.beam.api.services.impl;

import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.response.emotes.EmotePacksResponse;
import pro.beam.api.services.AbstractHTTPService;

public class EmotesService extends AbstractHTTPService {
    public EmotesService(BeamAPI beam) {
        super(beam, "/_latest/emoticons");
    }

    public ListenableFuture<EmotePacksResponse> defaultEmotes() {
        return this.get("manifest.json", EmotePacksResponse.class);
    }
}
