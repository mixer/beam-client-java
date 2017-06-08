package com.mixer.api.response.channels;

import com.google.gson.annotations.SerializedName;
import com.mixer.api.resource.channel.MixerChannel;

import java.util.ArrayList;

public class ShowChannelsResponse extends ArrayList<MixerChannel> {
    public static enum Attributes {
        @SerializedName("online") ONLINE,
        @SerializedName("featured") FEATURED,
        @SerializedName("partnered") PARTNERED,
        @SerializedName("name") NAME,
        @SerializedName("viewersCurrent") VIEWERS_CURRENT,
        @SerializedName("viewersTotal") VIEWERS_TOTAL,
        @SerializedName("followers") FOLLOWERS,
        @SerializedName("subscribers") SUBSCRIBERS,
        @SerializedName("interactive") INTERACTIVE;
    }

    public static enum Scope {
        @SerializedName("names") NAMES,
        @SerializedName("titles") TITLES,
        @SerializedName("types") TYPES,
        @SerializedName("names") ALL;
    }

    public static enum Ordering {
        @SerializedName("asc") ASCENDING,
        @SerializedName("desc") DESCENDING;
    }
}
