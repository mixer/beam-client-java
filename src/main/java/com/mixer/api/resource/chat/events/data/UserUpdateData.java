package com.mixer.api.resource.chat.events.data;

import com.mixer.api.resource.MixerUser;
import com.mixer.api.resource.chat.AbstractChatEvent;

import java.util.List;

public class UserUpdateData extends AbstractChatEvent.EventData {
	public List<MixerUser.Role> roles;
	public int user;
}
