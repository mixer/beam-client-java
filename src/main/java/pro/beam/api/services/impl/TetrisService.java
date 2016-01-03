package pro.beam.api.services.impl;

import com.google.common.util.concurrent.CheckedFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.exceptions.BeamException;
import pro.beam.api.futures.checkers.Tetris;
import pro.beam.api.resource.channel.BeamChannel;
import pro.beam.api.resource.tetris.RobotInfo;
import pro.beam.api.services.AbstractHTTPService;

public class TetrisService extends AbstractHTTPService {
    public TetrisService(BeamAPI beam) {
        super(beam, "tetris");
    }

    public CheckedFuture<RobotInfo, BeamException> getRobotCredentials(int channelId) {
        BeamChannel channel = new BeamChannel();
        channel.id = channelId;

        return this.getRobotCredentials(channel);
    }

    public CheckedFuture<RobotInfo, BeamException> getRobotCredentials(BeamChannel channel) {
        return new Tetris.UnsetGameChecker().check(
            this.get(String.format("%d/robot", channel.id), RobotInfo.class)
        );
    }
}
