package com.mixer.api.response.channels;

import com.google.common.collect.ImmutableMap;
import com.mixer.api.resource.channel.MixerChannel;

import java.util.ArrayList;

public class ShowSlugsRepsonse extends ArrayList<MixerChannel.Type> {
    public static enum OnlineRestriction {
        ONLINE, OFFLINE, NONE;

        // XXX(ttaylorr): temporary work-around instead of building full API around this.
        public boolean putParams(ImmutableMap.Builder<String, Object> builder) {
            switch (this) {
                case ONLINE:
                    builder.put("where", "online.neq.0");
                    return true;
                case OFFLINE:
                    builder.put("where", "online.eq.0");
                    return true;
                case NONE:
                default:
                    return false;
            }
        }
    }
}
