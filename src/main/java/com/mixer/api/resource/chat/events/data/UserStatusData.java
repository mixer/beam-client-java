package com.mixer.api.resource.chat.events.data;

import com.mixer.api.resource.MixerUser;
import com.mixer.api.resource.chat.AbstractChatEvent;

import java.util.List;

public class UserStatusData extends AbstractChatEvent.EventData {
    public String username;
    public List<MixerUser.Role> roles;
    public String id;
}
