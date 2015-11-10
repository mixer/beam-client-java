package pro.beam.api.services.impl;

import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.resource.channel.BeamChannel;
import pro.beam.api.resource.tetris.RobotInfo;
import pro.beam.api.services.AbstractHTTPService;

public class TetrisService extends AbstractHTTPService {
    public TetrisService(BeamAPI beam) {
        super(beam, "tetris");
    }

    public ListenableFuture<RobotInfo> getRobotCredentials(int channelId) {
        BeamChannel channel = new BeamChannel();
        channel.id = channelId;

        return this.getRobotCredentials(channel);
    }

    public ListenableFuture<RobotInfo> getRobotCredentials(BeamChannel channel) {
        return this.get(String.format("%d/robot", channel.id), RobotInfo.class);
    }
}
