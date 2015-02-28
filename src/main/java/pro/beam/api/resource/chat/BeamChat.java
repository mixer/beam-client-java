package pro.beam.api.resource.chat;

import pro.beam.api.BeamAPI;
import pro.beam.api.resource.AbstractBeamConnectableResource;

import java.util.Random;

public class BeamChat extends AbstractBeamConnectableResource<BeamChatConnectable> {
    public String authkey;
    public String[] endpoints;
    public boolean linksAllowed;
    public boolean linksClickable;
    public String role;
    public double slowchat;

    @Override
    public BeamChatConnectable makeConnectable(BeamAPI beam) {
        return this.useWSSProtocol(new BeamChatConnectable(beam, this.selectEndpoint(), this));
    }

    private String selectEndpoint() {
        return this.endpoints[new Random().nextInt(this.endpoints.length)];
    }
}
