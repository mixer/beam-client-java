package pro.beam.api.services.impl;

import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.resource.chat.BeamChat;
import pro.beam.api.services.AbstractHTTPService;

public class ChatService extends AbstractHTTPService {
    public ChatService(BeamAPI beam) {
        super(beam, "chats");
    }

    public ListenableFuture<BeamChat> findOne(int id) {
        return this.get(String.valueOf(id), BeamChat.class);
    }
}
