package com.mixer.api.resource.channel;

import com.mixer.api.resource.MixerUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class MixerChannelStatus implements Serializable {
    public ArrayList<MixerUser.Role> roles;
    public FollowsRecord follows;

    public static class FollowsRecord implements Serializable {
        public int user;
        public int channel;
        public Date createdAt;
        public Date updatedAt;
    }
}
