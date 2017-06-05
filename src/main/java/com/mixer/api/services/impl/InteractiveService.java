package com.mixer.api.services.impl;

import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.mixer.api.BeamAPI;
import com.mixer.api.exceptions.BeamException;
import com.mixer.api.futures.checkers.Interactive;
import com.mixer.api.resource.channel.BeamChannel;
import com.mixer.api.resource.interactive.InteractiveGame;
import com.mixer.api.resource.interactive.InteractiveVersion;
import com.mixer.api.resource.interactive.RobotInfo;
import com.mixer.api.services.AbstractHTTPService;

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
