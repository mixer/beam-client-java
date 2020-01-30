package com.mixer.api.resource.chat.events.data;

import com.google.gson.annotations.SerializedName;
import com.mixer.api.resource.MixerUser;
import com.mixer.api.resource.chat.AbstractChatEvent;

import java.util.List;

public class PurgeMessageData extends AbstractChatEvent.EventData {
	public String user_name;
	public int user_id;
	public List<MixerUser.Role> user_roles;
	public int user_level;
	@SerializedName("user_id")public int message_owner_id;
}
