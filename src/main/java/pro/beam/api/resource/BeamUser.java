package pro.beam.api.resource;

import java.util.Date;

import pro.beam.api.resource.channel.BeamChannel;

import com.google.gson.annotations.SerializedName;

public class BeamUser {
    public Date createdAt;
    public String email;
    public int id;
    public BeamChannel channel;
    public int points;
    public Date resetTime;
    public String social_facebook;
    public String social_twitter;
    public String social_youtube;
    public Date updatedAt;
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
