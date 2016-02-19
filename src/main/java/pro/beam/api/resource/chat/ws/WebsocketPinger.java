package pro.beam.api.resource.chat.ws;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;

public class WebsocketPinger implements Runnable {
    protected final WebSocketClient ws;

    public WebsocketPinger(WebSocketClient ws) {
        this.ws = ws;
    }

    @Override public void run() {
        while (true) {
            this.ping();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
                return;
            }
        }
    }

    private void ping() {
        this.ws.sendFrame(new FramedataImpl1(Framedata.Opcode.PING));
    }
}
