package pro.beam.api.response.channels;

import pro.beam.api.resource.channel.BeamChannelStatus;

import java.io.Serializable;

public class ChannelStatusResponse implements Serializable {
    public int channel;
    public BeamChannelStatus status;
}
