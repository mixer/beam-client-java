package pro.beam.api.resource.chat;

import org.java_websocket.handshake.ServerHandshake;
import pro.beam.api.BeamAPI;
import pro.beam.api.http.ws.BeamWebSocketResource;

import java.net.URI;

public class BeamChatConnectable extends BeamWebSocketResource {
    protected final BeamChat chat;

    public BeamChatConnectable(BeamAPI beam, String endpoint, BeamChat chat) {
        super(URI.create(endpoint), beam);
        this.chat = chat;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println(serverHandshake);
    }

    @Override
    public void onMessage(String s) {
        System.out.println(s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    public void sendDatagram(ChatDatagram datagram) {
        this.send(this.beam.gson.toJson(datagram).getBytes());
    }
}
