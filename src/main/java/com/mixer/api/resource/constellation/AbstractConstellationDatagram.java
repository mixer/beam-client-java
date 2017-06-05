package com.mixer.api.resource.constellation;

import com.google.gson.annotations.SerializedName;

public abstract class AbstractConstellationDatagram {
    public Type type;

    public static enum Type {
        @SerializedName("method") METHOD,
        @SerializedName("event") EVENT,
        @SerializedName("reply") REPLY,
    }
}
