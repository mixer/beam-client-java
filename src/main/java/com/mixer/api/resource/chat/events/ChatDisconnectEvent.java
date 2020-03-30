package com.mixer.api.resource.chat.events;

import com.mixer.api.resource.chat.AbstractChatEvent;
import com.mixer.api.resource.chat.events.data.ChatDisconnectData;

public class ChatDisconnectEvent extends AbstractChatEvent<ChatDisconnectData> {
	public ChatDisconnectEvent() {
		this.event = EventType.DISCOUNNECT;
	}
}
