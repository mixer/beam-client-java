package pro.beam.api.resource;

import com.google.gson.annotations.SerializedName;
import pro.beam.api.resource.channel.BeamChannel;

import java.util.Date;

public class BeamUser {
    public Date created_at;
    public String display_name;
    public String email;
    public int id;
    public BeamChannel channel;
    public int points;
    public Date reset_time;
    public String social_facebook;
    public String social_twitter;
    public String social_youtube;
    public Date updated_at;
    public String username;
    public boolean verified;

    public enum Role {
        @SerializedName("Banned") BANNED,
        @SerializedName("User") USER,
        @SerializedName("Premium") PREMIUM,
        @SerializedName("Subscriber") SUBSCRIBER,
        @SerializedName("Mod") MOD,
        @SerializedName("Global Mod") GLOBAL_MOD,
        @SerializedName("Admin") ADMIN,
        @SerializedName("Developer") DEVELOPER,
        @SerializedName("Owner") OWNER
    }
}
