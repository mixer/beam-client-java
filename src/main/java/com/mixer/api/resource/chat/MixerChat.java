package com.mixer.api.resource.chat;

import com.mixer.api.MixerAPI;
import com.mixer.api.resource.chat.ws.MixerChatConnectable;

import java.net.URI;
import java.util.Random;

public class MixerChat {
    public String authkey;
    public String[] endpoints;
    public boolean linksAllowed;
    public boolean linksClickable;
    public String role;
    public double slowchat;

    public MixerChatConnectable connectable(MixerAPI mixer) {
        return new MixerChatConnectable(mixer, this);
    }

    public URI endpoint() {
        if (this.endpoints == null) {
            return null;
        } else {
            int index = new Random().nextInt(this.endpoints.length);
            return URI.create(this.endpoints[index]);
        }
    }
}
