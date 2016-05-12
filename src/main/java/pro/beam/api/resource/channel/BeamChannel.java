package pro.beam.api.resource.channel;

import java.io.Serializable;
import java.util.*;

import com.google.gson.annotations.SerializedName;
import pro.beam.api.resource.BeamUser;

public class BeamChannel implements Serializable {
    public int id;
    public String token;
    public boolean online;
    public boolean featured;
    public boolean partnered;
    public boolean transcodingEnabled;
    public boolean suspended;
    public boolean interactive;
    public boolean hasVod;
    public String name;
    public AudienceRating audience;
    public String streamKey;
    public int viewersTotal;
    public int viewersCurrent;
    public int numFollowers;
    public int numSubscribers;
    public String description;
    public int typeId;
    public Date createdAt;
    public Date updatedAt;
    public int userId;
    public int coverId;
    public int thumbnailId;
    public int badgeId;
    public BeamResource thumbnail;
    public BeamResource cover;
    public BeamResource badge;
    public Type type;
    public Map<String, Object> preferences;
    @Deprecated @SerializedName("cache") public ArrayDeque<CachedMessage> messageCache;
    public BeamUser user;

    public static class Type implements Serializable {
        public int id;
        public String name;
        public String parent;
        public String description;
        public String source;
        public int viewersCurrent;
        public int online;
        public String coverUrl;
    }

    public static enum CostreamPreference {
        @SerializedName("all") ALL,
        @SerializedName("following") FOLLOWING,
        @SerializedName("none") NONE
    }

    public static enum AudienceRating {
        @SerializedName("family") FAMILY,
        @SerializedName("teen") TEEN,
        @SerializedName("18+") ADULT
    }
}
