package com.mixer.api.resource.constellation.events.data;

import com.mixer.api.resource.constellation.AbstractConstellationEvent;

public class ConstellationDisconnectData extends AbstractConstellationEvent.EventData {
    public int code;
    public String reason;
    public boolean remote;
	public ConstellationDisconnectData(int code, String reason, boolean remote) {
		super();
		this.code = code;
		this.reason = reason;
		this.remote = remote;
	}
}
