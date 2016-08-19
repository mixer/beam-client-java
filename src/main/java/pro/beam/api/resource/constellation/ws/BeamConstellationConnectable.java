package pro.beam.api.resource.constellation.ws;

import pro.beam.api.BeamAPI;
import pro.beam.api.resource.constellation.AbstractConstellationEvent;
import pro.beam.api.resource.constellation.BeamConstellation;
import pro.beam.api.resource.constellation.AbstractConstellationMethod;
import pro.beam.api.resource.constellation.AbstractConstellationReply;
import pro.beam.api.resource.constellation.events.EventHandler;
import pro.beam.api.resource.constellation.replies.ReplyHandler;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

public class BeamConstellationConnectable {
    private static final SSLSocketFactory SSL_SOCKET_FACTORY = (SSLSocketFactory) SSLSocketFactory.getDefault();
    private static final String DISCONNECT_MSG = "BEAM_JAVA DISCONNECT";

    protected final BeamAPI beam;
    protected final BeamConstellation constellation;

    private final Object connectionLock = false;
    private BeamConstellationConnection connection;

    public BeamConstellationConnectable(BeamAPI beam, BeamConstellation constellation) {
        this.beam = beam;
        this.constellation = constellation;
    }

    public boolean connect() {
        synchronized (this.connectionLock) {
            BeamConstellationConnection newConnection = new BeamConstellationConnection(this, this.beam, this.constellation);
            if (this.connection != null) {
                newConnection.inherit(this.connection);
            }

            try {
                newConnection.setSocket(SSL_SOCKET_FACTORY.createSocket());
            } catch (IOException e) {
                return false;
            }

            try {
                newConnection.connectBlocking();
            } catch (InterruptedException e) {
                return false;
            }
            this.connection = newConnection;

            return true;
        }
    }

    /**
     * Causes the websocket to be closed and not reconnected.
     */
    public void disconnect() {
        this.disconnect(1000, DISCONNECT_MSG);
    }

    /**
     * Causes the websocket to be closed, but reconnected.
     *
     * @param code Websocket disconnect code
     * @param msg Websocket disconnect message
     */
    public void disconnect(int code, String msg) {
        synchronized (this.connectionLock) {
            if (this.connection == null) {
                return;
            }

            this.connection.closeConnection(code, msg);
        }
    }

    public boolean isClosed() {
        return connection.isClosed();
    }

    public boolean isClosing() {
        return connection.isClosing();
    }

    public boolean isConnecting() {
        return connection.isConnecting();
    }

    public boolean isOpen() {
        return connection.isOpen();
    }

    protected void notifyClose(int i, String s, boolean b) {
        if (DISCONNECT_MSG.equals(s)) {
            return;
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) { }

        this.connect();
    }

    public <T extends AbstractConstellationEvent> boolean on(Class<T> eventType, EventHandler<T> handler) {
        synchronized (this.connectionLock) {
            return this.connection.on(eventType, handler);
        }
    }

    public void send(AbstractConstellationMethod method) {
        this.send(method, null);
    }

    public <T extends AbstractConstellationReply> void send(final AbstractConstellationMethod method, ReplyHandler<T> handler) {
        synchronized (this.connectionLock) {
            this.connection.send(method, handler);
        }
    }
}
