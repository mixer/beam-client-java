package pro.beam.api.resource;

import java.util.Date;
import java.util.List;

import pro.beam.api.resource.channel.BeamChannel;

import com.google.gson.annotations.SerializedName;
import pro.beam.api.resource.channel.BeamResource;

public class BeamUser {
    public Date createdAt;
    public String email;
    public int id;
    public BeamChannel channel;
    public int points;
    public Date resetTime;
    public Social social;
    public Date updatedAt;
    public String username;
    public boolean verified;
    public List<BeamResource> avatars;

    public enum Role {
        @SerializedName("Banned") BANNED,
        @SerializedName("User") USER,
        @SerializedName("Pro") PRO,
        @SerializedName("Subscriber") SUBSCRIBER,
        @SerializedName("Mod") MOD,
        @SerializedName("Global Mod") GLOBAL_MOD,
        @SerializedName("Admin") ADMIN,
        @SerializedName("Developer") DEVELOPER,
        @SerializedName("Owner") OWNER
    }

    public class Social {
        public String facebook;
        public String twitter;
        public String youtube;
    }
}
