package com.mixer.api.resource.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PasswordScore {
    public String password;
    public double entropy;
    public @SerializedName("match_sequence")
    List<MatchItem> matchSequence;
    public @SerializedName("crack_time") double crackTime;
    public @SerializedName("crack_time_display") String crackTimeDisplay;
    public int score;
    public @SerializedName("calc_time") double calcTime;

    public static class MatchItem {
        public String pattern;
        public int i;
        public int j;
        public String token;
        public double entropy;
        public int cardinality;
    }
}
