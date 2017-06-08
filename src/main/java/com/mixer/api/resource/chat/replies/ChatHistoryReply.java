package com.mixer.api.resource.chat.replies;

import com.google.gson.annotations.SerializedName;
import com.mixer.api.resource.chat.AbstractChatReply;
import com.mixer.api.resource.chat.events.data.IncomingMessageData;

import java.util.List;

public class ChatHistoryReply extends AbstractChatReply {
    public @SerializedName("data") List<IncomingMessageData> messages;
}
