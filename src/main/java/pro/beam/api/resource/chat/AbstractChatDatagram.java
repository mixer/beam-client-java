package pro.beam.api.resource.chat;

import com.google.gson.annotations.SerializedName;

public abstract class AbstractChatDatagram {
    public Type type;

    public static enum Type {
        @SerializedName("method") METHOD,
        @SerializedName("event") EVENT,
        @SerializedName("reply") REPLY,
    }
}
