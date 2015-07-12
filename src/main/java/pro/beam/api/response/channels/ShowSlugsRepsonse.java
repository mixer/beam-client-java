package pro.beam.api.response.channels;

import pro.beam.api.resource.channel.BeamChannel;

import java.util.ArrayList;

public class ShowSlugsRepsonse extends ArrayList<BeamChannel.Type> {
    public static enum OnlineRestriction {
        ONLINE, OFFLINE, NONE
    }
}
