package pro.beam.api.services;

import pro.beam.api.BeamAPI;

public abstract class AbstractBeamService extends AbstractService {
    protected final BeamAPI beam;

    public AbstractBeamService(BeamAPI beam) {
        this.beam = beam;
    }
}
