package com.mixer.api.services;

import com.mixer.api.MixerAPI;

public abstract class AbstractMixerService extends AbstractService {
    protected final MixerAPI mixer;

    public AbstractMixerService(MixerAPI mixer) {
        this.mixer = mixer;
    }
}
