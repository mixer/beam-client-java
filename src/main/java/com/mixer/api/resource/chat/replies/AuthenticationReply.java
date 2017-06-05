package com.mixer.api.resource.chat.replies;

import com.mixer.api.resource.MixerUser;
import com.mixer.api.resource.chat.AbstractChatReply;

import java.util.ArrayList;

public class AuthenticationReply extends AbstractChatReply {
    public boolean authenticated;
    public ArrayList<MixerUser.Role> roles;
}
