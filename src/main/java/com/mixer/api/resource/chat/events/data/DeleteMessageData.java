package com.mixer.api.resource.chat.events.data;

import com.mixer.api.resource.MixerUser;
import com.mixer.api.resource.chat.AbstractChatEvent;

import java.util.List;
import java.util.UUID;

public class DeleteMessageData extends AbstractChatEvent.EventData {
    public UUID id;
    public String user_name;
    public int user_id;
	public List<MixerUser.Role> user_roles;
    public int user_level;
}
