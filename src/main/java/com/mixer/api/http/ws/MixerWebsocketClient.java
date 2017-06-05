package com.mixer.api.http.ws;

import com.mixer.api.MixerAPI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

public class MixerWebsocketClient extends WebSocketClient {
    protected final MixerAPI mixer;

    public MixerWebsocketClient(MixerAPI mixer, URI uri) {
        super(uri, new CookieDraft_17(mixer.http));

        this.mixer = mixer;
    }

    public MixerWebsocketClient(MixerAPI mixer, URI uri, Map<String, String> httpHeaders) {
        super(uri, new CookieDraft_17(mixer.http), httpHeaders, 0);

        this.mixer = mixer;
    }

    @Override public void onOpen(ServerHandshake serverHandshake) { }
    @Override public void onMessage(String s) { }
    @Override public void onClose(int i, String s, boolean b) { }
    @Override public void onError(Exception e) { }
}
