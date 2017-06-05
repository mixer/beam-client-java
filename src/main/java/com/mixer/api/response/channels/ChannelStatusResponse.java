package com.mixer.api.response.channels;

import com.mixer.api.resource.channel.BeamChannelStatus;

import java.io.Serializable;

public class ChannelStatusResponse implements Serializable {
    public int channel;
    public BeamChannelStatus status;
}
