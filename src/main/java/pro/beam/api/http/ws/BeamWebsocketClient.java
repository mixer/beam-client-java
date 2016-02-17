package pro.beam.api.http.ws;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import pro.beam.api.BeamAPI;

import java.net.URI;

public class BeamWebsocketClient extends WebSocketClient {
    protected final BeamAPI beam;

    public BeamWebsocketClient(BeamAPI beam, URI uri) {
        super(uri, new CookieDraft_17(beam.http));

        this.beam = beam;
    }

    @Override public void onOpen(ServerHandshake serverHandshake) { }
    @Override public void onMessage(String s) { }
    @Override public void onClose(int i, String s, boolean b) { }
    @Override public void onError(Exception e) { }
}
