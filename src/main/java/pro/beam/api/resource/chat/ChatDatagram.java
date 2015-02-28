package pro.beam.api.resource.chat;

import com.google.gson.annotations.SerializedName;

public abstract class ChatDatagram {
    public Type type;

    public static enum Type {
        @SerializedName("message") MESSAGE,
        @SerializedName("event") EVENT
    }
}
