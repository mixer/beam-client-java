package pro.beam.api.resource;

import pro.beam.api.resource.ws.AbstractBeamConnectableResource;
import pro.beam.api.resource.ws.BeamChatConnectable;

import java.util.Random;

public class BeamChat extends AbstractBeamConnectableResource<BeamChatConnectable> {
    public String authkey;
    public String[] endpoints;
    public boolean linksAllowed;
    public boolean linksClickable;
    public String role;
    public double slowchat;

    @Override
    public BeamChatConnectable makeConnectable() {
        return this.useWSSProtocol(new BeamChatConnectable(this.selectEndpoint(), this));
    }

    private String selectEndpoint() {
        return this.endpoints[new Random().nextInt(this.endpoints.length)];
    }
}
