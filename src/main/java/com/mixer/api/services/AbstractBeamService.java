package com.mixer.api.services;

import com.mixer.api.BeamAPI;

public abstract class AbstractBeamService extends AbstractService {
    protected final BeamAPI beam;

    public AbstractBeamService(BeamAPI beam) {
        this.beam = beam;
    }
}
