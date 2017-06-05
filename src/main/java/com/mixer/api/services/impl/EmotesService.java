package com.mixer.api.services.impl;

import com.google.common.util.concurrent.ListenableFuture;
import com.mixer.api.MixerAPI;
import com.mixer.api.response.emotes.EmotePacksResponse;
import com.mixer.api.services.AbstractHTTPService;

public class EmotesService extends AbstractHTTPService {
    public EmotesService(MixerAPI mixer) {
        super(mixer, "/_latest/emoticons");
    }

    public ListenableFuture<EmotePacksResponse> defaultEmotes() {
        return this.get("manifest.json", EmotePacksResponse.class);
    }
}
