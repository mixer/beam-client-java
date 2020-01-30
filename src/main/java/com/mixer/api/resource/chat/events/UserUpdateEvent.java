package com.mixer.api.resource.chat.events;

import com.mixer.api.resource.chat.AbstractChatEvent;
import com.mixer.api.resource.chat.events.data.UserUpdateData;

public class UserUpdateEvent extends AbstractChatEvent<UserUpdateData> {
	public UserUpdateEvent() {
		this.event = EventType.USER_UPDATE;
	}
}
