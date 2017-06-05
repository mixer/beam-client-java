package com.mixer.api.resource.chat;

import com.mixer.api.resource.MixerUser;

import java.util.List;

public class OnlineChatUser {
    public String userName;
    public List<MixerUser.Role> userRoles;
    public int userId;
}
