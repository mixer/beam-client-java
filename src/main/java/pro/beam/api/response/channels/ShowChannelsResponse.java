package pro.beam.api.response.channels;

import com.google.gson.annotations.SerializedName;
import pro.beam.api.resource.channel.BeamChannel;

import java.util.ArrayList;

public class ShowChannelsResponse extends ArrayList<BeamChannel> {
    public static enum Attributes {
        @SerializedName("online") ONLINE,
        @SerializedName("featured") FEATURED,
        @SerializedName("partnered") PARTNERED,
        @SerializedName("name") NAME,
        @SerializedName("viewers_total") VIEWERS_TOTAL,
        @SerializedName("followers") FOLLOWERS,
        @SerializedName("subscribers") SUBSCRIBERS;
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
