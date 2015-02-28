package pro.beam.api.http.ws;

import org.java_websocket.client.WebSocketClient;

public interface ConnectionProducer<T extends WebSocketClient> {
    T makeConnectable();
}
