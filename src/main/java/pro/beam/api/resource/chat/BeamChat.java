package pro.beam.api.resource.chat;

import pro.beam.api.BeamAPI;
import pro.beam.api.http.ws.ConnectionProducer;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.URI;
import java.util.Random;

public class BeamChat implements ConnectionProducer<BeamChatConnectable> {
    public String authkey;
    public String[] endpoints;
    public boolean linksAllowed;
    public boolean linksClickable;
    public String role;
    public double slowchat;

    @Override
    public BeamChatConnectable makeConnectable(BeamAPI beam) {
        BeamChatConnectable connectable = new BeamChatConnectable(beam, this.selectEndpoint(), this);

        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try {
            connectable.setSocket(sf.createSocket());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connectable;
    }

    protected URI selectEndpoint() {
        if (this.endpoints == null) {
            return null;
        } else {
            int index = new Random().nextInt(this.endpoints.length);
            return URI.create(this.endpoints[index]);
        }
    }
}
