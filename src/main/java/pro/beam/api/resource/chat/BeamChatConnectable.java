package pro.beam.api.resource.chat;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import pro.beam.api.BeamAPI;
import pro.beam.api.http.ws.CookieDraft_17;

import java.net.URI;

public class BeamChatConnectable extends WebSocketClient {
    protected final BeamAPI beam;
    protected final BeamChat chat;

    public BeamChatConnectable(BeamAPI beam, String endpoint, BeamChat chat) {
        super(URI.create(endpoint), new CookieDraft_17(beam.http));

        this.beam = beam;
        this.chat = chat;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
    }

    @Override
    public void onMessage(String s) {
    }

    @Override
    public void onClose(int i, String s, boolean b) {
    }

    @Override
    public void onError(Exception e) {
    }
}
