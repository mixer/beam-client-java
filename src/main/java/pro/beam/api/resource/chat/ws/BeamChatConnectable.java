package pro.beam.api.resource.chat.ws;

import pro.beam.api.BeamAPI;
import pro.beam.api.resource.chat.AbstractChatEvent;
import pro.beam.api.resource.chat.AbstractChatMethod;
import pro.beam.api.resource.chat.AbstractChatReply;
import pro.beam.api.resource.chat.BeamChat;
import pro.beam.api.resource.chat.events.EventHandler;
import pro.beam.api.resource.chat.events.data.IncomingMessageData;
import pro.beam.api.resource.chat.methods.AuthenticateMessage;
import pro.beam.api.resource.chat.replies.ReplyHandler;

import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;

public class BeamChatConnectable {
    private static final SSLSocketFactory SSL_SOCKET_FACTORY = (SSLSocketFactory) SSLSocketFactory.getDefault();
    private static final String DISCONNECT_MSG = "BEAM_JAVA DISCONNECT";

    protected final BeamAPI beam;
    protected final BeamChat chat;

    private final Object connectionLock = false;
    private BeamChatConnection connection;
    private AuthenticateMessage auth;

    public BeamChatConnectable(BeamAPI beam, BeamChat chat) {
        this.beam = beam;
        this.chat = chat;
    }

    public boolean connect() {
        synchronized (this.connectionLock) {
            BeamChatConnection newConnection = new BeamChatConnection(this, this.beam, this.chat);
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
        this.connection.closeConnection(code, msg);
    }

    protected void notifyClose(int i, String s, boolean b) {
        if (DISCONNECT_MSG.equals(s)) {
            return;
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) { }

        this.connect();

        if (this.auth != null) {
            this.send(this.auth);
        }
    }

    public <T extends AbstractChatEvent> boolean on(Class<T> eventType, EventHandler<T> handler) {
        synchronized (this.connectionLock) {
            return this.connection.on(eventType, handler);
        }
    }

    public void send(AbstractChatMethod method) {
        this.send(method, null);
    }

    public <T extends AbstractChatReply> void send(final AbstractChatMethod method, ReplyHandler<T> handler) {
        synchronized (this.connectionLock) {
            if (method instanceof AuthenticateMessage) {
                this.auth = (AuthenticateMessage) method;
            }

            this.connection.send(method, handler);
        }
    }

    public void delete(IncomingMessageData message) {
        synchronized (this.connectionLock) {
            this.connection.delete(message);
        }
    }
}
