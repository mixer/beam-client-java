package pro.beam.api.http.ws;

import org.java_websocket.client.WebSocketClient;
import pro.beam.api.BeamAPI;

public interface ConnectionProducer<T extends WebSocketClient> {
    T makeConnectable(BeamAPI beam);
}
