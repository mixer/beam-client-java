package pro.beam.api.resource.chat;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class BeamChatConnectable extends WebSocketClient {
    protected final BeamChat chat;

    public BeamChatConnectable(String endpoint, BeamChat chat) {
        super(URI.create(endpoint));
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
