package pro.beam.api.response.channels;

import com.google.common.collect.ImmutableMap;
import pro.beam.api.resource.channel.BeamChannel;

import java.util.ArrayList;

public class ShowSlugsRepsonse extends ArrayList<BeamChannel.Type> {
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
