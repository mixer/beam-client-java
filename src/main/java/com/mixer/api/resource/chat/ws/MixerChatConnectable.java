package com.mixer.api.resource.chat.ws;

import com.mixer.api.MixerAPI;
import com.mixer.api.resource.chat.AbstractChatEvent;
import com.mixer.api.resource.chat.AbstractChatMethod;
import com.mixer.api.resource.chat.AbstractChatReply;
import com.mixer.api.resource.chat.MixerChat;
import com.mixer.api.resource.chat.events.EventHandler;
import com.mixer.api.resource.chat.events.data.IncomingMessageData;
import com.mixer.api.resource.chat.methods.AuthenticateMessage;
import com.mixer.api.resource.chat.replies.ReplyHandler;

import javax.net.ssl.SSLSocketFactory;

import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MixerChatConnectable {
    private static final SSLSocketFactory SSL_SOCKET_FACTORY = (SSLSocketFactory) SSLSocketFactory.getDefault();
    private static final String DISCONNECT_MSG = "MIXER_JAVA DISCONNECT";

    protected final MixerAPI mixer;
    protected final MixerChat chat;

    private final Object connectionLock = false;
    private MixerChatConnection connection;
    private AuthenticateMessage auth;
	
    private Timer pingTimer;
    
    public MixerChatConnectable(MixerAPI mixer, MixerChat chat) {
        this.mixer = mixer;
        this.chat = chat;
    }

    public boolean connect() {
        synchronized (this.connectionLock) {
            MixerChatConnection newConnection = new MixerChatConnection(this, this.mixer, this.chat);
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

            handlePing();

            return true;
        }
    }
	
    /**
     * Handles the automatic ping to the socket.
     */
    private void handlePing() {
        if (pingTimer != null) { 
            pingTimer.cancel();
        }

        pingTimer = (new Timer());
        pingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (connection.isOpen()) {
                    ping();
                }
            }
        }, 30 * 1000);
    }

    /**
     * Sends a ping to the websocket server.
     */
    private void ping()
    {
        FramedataImpl1 frame = FramedataImpl1.get(Framedata.Opcode.PING);
        frame.setFin(true);
        connection.sendFrame(frame);
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
			if (pingTimer != null) {
                pingTimer.cancel();
            }
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