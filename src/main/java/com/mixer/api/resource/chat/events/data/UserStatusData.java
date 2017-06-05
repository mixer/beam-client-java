package com.mixer.api.resource.chat.events.data;

import com.mixer.api.resource.BeamUser;
import com.mixer.api.resource.chat.AbstractChatEvent;

import java.util.List;

public class UserStatusData extends AbstractChatEvent.EventData {
    public String username;
    public List<BeamUser.Role> roles;
    public String id;
}
