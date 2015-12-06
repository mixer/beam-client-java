package pro.beam.api.resource.chat;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import pro.beam.api.BeamAPI;
import pro.beam.api.http.ws.CookieDraft_17;

import java.net.URI;

import pro.beam.api.resource.chat.events.EventHandler;
import pro.beam.api.resource.chat.events.data.IncomingMessageData;
import pro.beam.api.resource.chat.events.data.IncomingWidgetData;
import pro.beam.api.resource.chat.replies.ReplyHandler;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class BeamChatConnectable extends WebSocketClient {
    protected final BeamAPI beam;
    protected final BeamChat chat;

    protected final Map<Integer, ReplyPair> replyHandlers;
    protected final Multimap<Class<? extends AbstractChatEvent>, EventHandler> eventHandlers;

    public BeamChatConnectable(BeamAPI beam, URI endpoint, BeamChat chat) {
        super(endpoint, new CookieDraft_17(beam.http));

        this.beam = beam;
        this.chat = chat;

        this.replyHandlers = new ConcurrentHashMap<>(new HashMap<Integer, ReplyPair>());
        this.eventHandlers = HashMultimap.create();
    }

    public <T extends AbstractChatEvent> boolean on(Class<T> eventType, EventHandler<T> handler) {
        return this.eventHandlers.put(eventType, handler);
    }

    public void send(AbstractChatMethod method) {
        this.send(method, null);
    }

    public <T extends AbstractChatReply> void send(final AbstractChatMethod method, ReplyHandler<T> handler) {
        if (handler != null) {
            this.replyHandlers.put(method.id, ReplyPair.from(handler));
        }

        this.beam.executor.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                byte[] data = BeamChatConnectable.this.beam.gson.toJson(method).getBytes();
                BeamChatConnectable.this.send(data);

                return null;
            }
        });
    }

    public ListenableFuture<BeamChatConnectable> connectFuture() {
        return this.beam.executor.submit(new Callable<BeamChatConnectable>() {
            @Override public BeamChatConnectable call() throws Exception {
                BeamChatConnectable self = BeamChatConnectable.this;

                self.connectBlocking();
                return self;
            }
        });
    }

    public void delete(IncomingMessageData message) {
        String path = this.beam.basePath.resolve("chats/" + message.channel + "/message/" + message.id).toString();
        this.beam.http.delete(path, null);
    }

    private <T extends AbstractChatEvent> void dispatchEvent(T event) {
        Class<? extends AbstractChatEvent> eventType = event.getClass();

        for (EventHandler handler : this.eventHandlers.get(eventType)) {
            handler.onEvent(event);
        }
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
    }

    @Override
    public void onMessage(String s) {
        // XXX: terrible
        ReplyPair replyPair = null;
        try {
            // Parse out the generic JsonObject so we can pull out the ID element from it,
            //  since we cannot yet parse as a generic class.
            JsonObject e = this.beam.gson.fromJson(s, JsonObject.class);
            if (e.has("id")) {
                int id = e.get("id").getAsInt();

                // Try and remove a reply-pair, execute the #onSuccess method if we find
                // a matching reply-pair.
                if ((replyPair = this.replyHandlers.remove(id)) != null) {
                    Class<? extends AbstractChatDatagram> type = replyPair.type;

                    // Now that we have the type, we can appropriately parse out the value
                    // And call the #onSuccess method with the value.
                    AbstractChatDatagram datagram = this.beam.gson.fromJson(s, type);
                    replyPair.handler.onSuccess(type.cast(datagram));
                }
            } else if (e.has("event")) {
                // Handles cases of beam widgets (GiveawayBot) sending ChatMessage events
                if(e.getAsJsonObject("data").has("user_id") && e.getAsJsonObject("data").get("user_id").getAsInt() == -1) {
                    Class<? extends AbstractChatEvent> type = AbstractChatEvent.EventType.fromSerializedName("WidgetMessage").getCorrespondingClass();
                    this.dispatchEvent(this.beam.gson.fromJson(e, type));
                } else {
                   // Default ChatMessage event handling
                   String eventName = e.get("event").getAsString();
                   Class<? extends AbstractChatEvent> type = AbstractChatEvent.EventType.fromSerializedName(eventName).getCorrespondingClass();
                   this.dispatchEvent(this.beam.gson.fromJson(e, type));
                }
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

    @Override
    public void onClose(int i, String s, boolean b) {
        while (this.isClosed() && !this.isConnecting()) {
            try {
                this.uri = this.chat.selectEndpoint();
                this.connectBlocking();
            } catch (InterruptedException ignored) { }
        }
    }

    @Override
    public void onError(Exception e) {
    }

    private static class ReplyPair<T extends AbstractChatReply> {
        public ReplyHandler<T> handler;
        public Class<T> type;

        private static <T extends AbstractChatReply> ReplyPair<T> from(ReplyHandler<T> handler) {
            ReplyPair<T> pair = new ReplyPair<>();

            pair.handler = handler;
            pair.type = (Class<T>) ((ParameterizedType) handler.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

            return pair;
        }
    }
}
