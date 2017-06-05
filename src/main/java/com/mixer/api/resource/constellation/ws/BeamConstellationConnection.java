package com.mixer.api.resource.constellation.ws;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mixer.api.BeamAPI;
import com.mixer.api.http.ws.BeamWebsocketClient;
import com.mixer.api.resource.constellation.*;
import com.mixer.api.resource.constellation.events.EventHandler;
import com.mixer.api.resource.constellation.replies.ReplyHandler;
import pro.beam.api.resource.constellation.*;

import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.Callable;

public class BeamConstellationConnection extends BeamWebsocketClient {
    private static URI CONSTELLATION_ADDR = URI.create("wss://constellation.beam.pro");

    protected final BeamConstellationConnectable producer;
    protected final BeamConstellation constellation;

    protected final Map<Integer, ReplyPair> replyHandlers;
    protected final Multimap<Class<? extends AbstractConstellationEvent>, EventHandler> eventHandlers;

    public BeamConstellationConnection(BeamConstellationConnectable producer, BeamAPI beam, BeamConstellation constellation) {
        super(beam, CONSTELLATION_ADDR, beam.http.getDefaultSocketHeaders());
        this.producer = producer;


        this.constellation = constellation;

        this.replyHandlers = Maps.newConcurrentMap();
        this.eventHandlers = HashMultimap.create();
    }

    public void inherit(BeamConstellationConnection other) {
        for (Map.Entry<Class<? extends AbstractConstellationEvent>, EventHandler> entry : other.eventHandlers.entries()) {
            this.eventHandlers.put(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<Integer, ReplyPair> entry : other.replyHandlers.entrySet()) {
            this.replyHandlers.put(entry.getKey(), entry.getValue());
        }
    }

    public <T extends AbstractConstellationEvent> boolean on(Class<T> eventType, EventHandler<T> handler) {
        return this.eventHandlers.put(eventType, handler);
    }

    /**
     * Send sends a constellation method with no given response handler.
     * @param method The method to send.
     */
    public void send(AbstractConstellationMethod method) {
        this.send(method, null);
    }

    /**
     * Send sends a constellation method with a response handler that will be invoked when a response
     * is received from the constellation server.
     *
     * @param method The method to send.
     * @param handler The reply handler.
     * @param <T> The type on which to bind the method and reply handler together with.
     */
    public <T extends AbstractConstellationReply> void send(final AbstractConstellationMethod method, ReplyHandler<T> handler) {
        if (handler != null) {
            this.replyHandlers.put(method.id, ReplyPair.from(handler));
        }

        this.beam.executor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                byte[] data = BeamConstellationConnection.this.beam.gson.toJson(method).getBytes();
                BeamConstellationConnection.this.send(data);

                return null;
            }
        });
    }

    @Override
    public void onMessage(String s) {
        // XXX: terrible
        ReplyPair replyPair = null;
        try {
            // Parse out the generic JsonObject so we can pull out the ID element from it,
            //  since we cannot yet parse as a generic class.
            JsonObject e = new JsonParser().parse(s).getAsJsonObject();
            if (e.has("id")) {
                int id = e.get("id").getAsInt();

                // Try and remove a reply-pair, execute the #onSuccess method if we find
                // a matching reply-pair.
                if ((replyPair = this.replyHandlers.remove(id)) != null) {
                    Class<? extends AbstractConstellationDatagram> type = replyPair.type;

                    // Now that we have the type, we can appropriately parse out the value
                    // And call the #onSuccess method with the value.
                    AbstractConstellationDatagram datagram = this.beam.gson.fromJson(s, type);
                    replyPair.handler.onSuccess(type.cast(datagram));
                }
            } else if (e.has("event")) {
                // Default ChatMessage event handling
                String eventName = e.get("event").getAsString();
                Class<? extends AbstractConstellationEvent> type = AbstractConstellationEvent.EventType.fromSerializedName(eventName).getCorrespondingClass();
                this.dispatchEvent(this.beam.gson.fromJson(e, type));
            }
        } catch (JsonSyntaxException e) {
            // If an exception was called and we do have a reply handler to catch it,
            // call the #onFailure method with the throwable.
            if (replyPair != null) {
                replyPair.handler.onFailure(e);
            } else {
                throw e;
            }
        }
    }

    @Override public void onClose(int i, String s, boolean b) {
        this.producer.notifyClose(i, s, b);
    }

    private <T extends AbstractConstellationEvent> void dispatchEvent(T event) {
        Class<? extends AbstractConstellationEvent> eventType = event.getClass();

        for (EventHandler handler : this.eventHandlers.get(eventType)) {
            handler.onEvent(event);
        }
    }


    private static class ReplyPair<T extends AbstractConstellationReply> {
        public ReplyHandler<T> handler;
        public Class<T> type;

        private static <T extends AbstractConstellationReply> ReplyPair<T> from(ReplyHandler<T> handler) {
            ReplyPair<T> pair = new ReplyPair<>();

            pair.handler = handler;
            pair.type = (Class<T>) ((ParameterizedType) handler.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

            return pair;
        }
    }
}
