package pro.beam.api.services.impl;

import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.exceptions.BeamException;
import pro.beam.api.futures.checkers.Interactive;
import pro.beam.api.resource.channel.BeamChannel;
import pro.beam.api.resource.interactive.InteractiveGame;
import pro.beam.api.resource.interactive.InteractiveVersion;
import pro.beam.api.resource.interactive.RobotInfo;
import pro.beam.api.services.AbstractHTTPService;

public class InteractiveService extends AbstractHTTPService {
    public InteractiveService(BeamAPI beam) {
        super(beam, "interactive");
    }

    public CheckedFuture<RobotInfo, BeamException> getRobotCredentials(int channelId) {
        BeamChannel channel = new BeamChannel();
        channel.id = channelId;

        return this.getRobotCredentials(channel);
    }

    public CheckedFuture<RobotInfo, BeamException> getRobotCredentials(BeamChannel channel) {
        return new Interactive.UnsetGameChecker().check(
            this.get(String.format("%d/robot", channel.id), RobotInfo.class)
        );
    }

    public ListenableFuture<InteractiveGame> getInteractiveGame(int gameId) {
        return this.get(String.format("games/%d", gameId), InteractiveGame.class);
    }

    public ListenableFuture<InteractiveVersion> getInteractiveVersion(int versionId) {
        return this.get(String.format("versions/%d", versionId), InteractiveVersion.class);
    }
}
