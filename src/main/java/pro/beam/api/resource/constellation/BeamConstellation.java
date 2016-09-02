package pro.beam.api.resource.constellation;

import pro.beam.api.BeamAPI;
import pro.beam.api.resource.constellation.ws.BeamConstellationConnectable;

public class BeamConstellation {
    public BeamConstellationConnectable connectable(BeamAPI beam) {
        return new BeamConstellationConnectable(beam, this);
    }
}
