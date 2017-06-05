package com.mixer.api.response.channels;

import com.mixer.api.resource.channel.MixerChannelStatus;

import java.io.Serializable;

public class ChannelStatusResponse implements Serializable {
    public int channel;
    public MixerChannelStatus status;
}
