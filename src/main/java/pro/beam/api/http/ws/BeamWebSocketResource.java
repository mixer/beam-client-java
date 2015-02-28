package pro.beam.api.http.ws;

import org.java_websocket.client.WebSocketClient;
import pro.beam.api.BeamAPI;

import java.net.URI;

public abstract class BeamWebSocketResource extends WebSocketClient {
    protected BeamAPI beam;

    public BeamWebSocketResource(URI serverURI, BeamAPI beam) {
        super(serverURI);
        this.beam = beam;
    }
}
