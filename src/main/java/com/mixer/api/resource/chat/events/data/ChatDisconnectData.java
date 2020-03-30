package com.mixer.api.resource.chat.events.data;

import com.mixer.api.resource.chat.AbstractChatEvent;

public class ChatDisconnectData extends AbstractChatEvent.EventData {
	public int code;
	public String reason;
	public boolean remote;

	public ChatDisconnectData(int code, String reason, boolean remote) {
		super();
		this.code = code;
		this.reason = reason;
		this.remote = remote;
	}
}
