package com.mixer.api.resource.chat.events;

import com.mixer.api.resource.chat.AbstractChatEvent;
import com.mixer.api.resource.chat.events.data.WelcomeMessageData;

public class WelcomeEvent extends AbstractChatEvent<WelcomeMessageData> {
	public WelcomeEvent() {
		this.event = EventType.WELCOME;
	}
}
