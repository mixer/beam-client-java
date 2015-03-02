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
        System.out.println(serverHandshake.getHttpStatus());
    }

    @Override
    public void onMessage(String s) {
        System.out.println("message: "+s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("closed: ("+i+") "+s);
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    public void send(ChatDatagram datagram) {
        byte[] data = this.beam.gson.toJson(datagram).getBytes();
        this.send(this.beam.gson.toJson(datagram));
    }
}
