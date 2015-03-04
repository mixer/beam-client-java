package pro.beam.api.resource.channel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class BeamChannel {
    public CostreamPreference allow_costream;
    public AudienceRating audience;
    public String body;
    public Date created_at;
    public boolean featured;
    public int followers;
    public boolean hidden;
    public int id;
    public String name;
    public boolean online;
    public boolean partnered;
    public int subscribers;
    public String token;
    public String type;
    public Date updated_at;
    public int user;
    public int viewers_total;

    public static enum CostreamPreference {
        @SerializedName("all") ALL,
        @SerializedName("following") FOLOWING,
        @SerializedName("none") NONE
    }

    public static enum AudienceRating {
        G, PG, PG13, R
    }
}
