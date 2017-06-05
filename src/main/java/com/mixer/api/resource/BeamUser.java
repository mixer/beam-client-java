package com.mixer.api.resource;

import com.google.gson.annotations.SerializedName;
import com.mixer.api.resource.channel.BeamChannel;
import com.mixer.api.resource.channel.BeamResource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class BeamUser implements Serializable {
    public Date createdAt;
    public String email;
    public int id;
    public BeamChannel channel;
    public int points;
    public int experience;
    public int sparks;
    public Date resetTime;
    public Social social;
    public Date updatedAt;
    public String username;
    public boolean verified;
    public ArrayList<BeamResource> avatars;

    public enum Role {
        @SerializedName("Banned") BANNED,
        @SerializedName("Muted") MUTED,
        @SerializedName("User") USER,
        @SerializedName("Pro") PRO,
        @SerializedName("Subscriber") SUBSCRIBER,
        @SerializedName("Mod") MOD,
        @SerializedName("Global Mod") GLOBAL_MOD,
        @SerializedName("Founder") FOUNDER,
        @SerializedName("Staff") STAFF,
        @SerializedName("Owner") OWNER
    }

    public class Social implements Serializable {
        public String facebook;
        public String twitter;
        public String youtube;
        public String discord;
    }
}
