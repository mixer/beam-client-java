package pro.beam.api.resource.channel;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.Timeable;

public class BeamChannel {
    public int id;
    public String token;
    public boolean online;
    public boolean featured;
    public boolean partnered;
    public boolean transcodingEnabled;
    public boolean suspended;
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
    public Status status;
    @SerializedName("cache") public ArrayDeque<CachedMessage> messageCache;
    public BeamUser user;

    public static class Type {
        public int id;
        public String name;
        public String parent;
        public String description;
        public String source;
        public int viewersCurrent;
        public int online;
    }

    public static class Status {
        public List<String> roles;
        public FollowsRecord follows;

        public static class FollowsRecord extends Timeable {
            public int user;
            public int channel;
        }
    }

    public static enum CostreamPreference {
        @SerializedName("all") ALL,
        @SerializedName("following") FOLOWING,
        @SerializedName("none") NONE
    }

    public static enum AudienceRating {
        G, PG, PG13, R
    }
}
