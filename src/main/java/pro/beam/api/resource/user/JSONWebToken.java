package pro.beam.api.resource.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONWebToken {
    public Long exp;
    public Long iat;
    public String iss;
    public String jti;
    public Long sub;
    public String btyp;

    public static class Group {
        public Long id;
        public String name;
    }
    public Group[] groups;

    public boolean hasExpired() {
        return System.currentTimeMillis() / 1000L >= exp;
    }
}
