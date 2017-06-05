package com.mixer.api.services.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.mixer.api.BeamAPI;
import com.mixer.api.response.emotes.EmotePacksResponse;
import com.mixer.api.services.AbstractHTTPService;

public class EmotesService extends AbstractHTTPService {
    public EmotesService(BeamAPI beam) {
        super(beam, "/_latest/emoticons");
    }

    public ListenableFuture<EmotePacksResponse> defaultEmotes() {
        return this.get("manifest.json", EmotePacksResponse.class);
    }
}
