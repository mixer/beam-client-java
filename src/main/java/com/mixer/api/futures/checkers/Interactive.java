package com.mixer.api.futures.checkers;

import com.google.common.collect.ImmutableMap;
import com.mixer.api.exceptions.BeamException;
import com.mixer.api.exceptions.UnAuthorizedException;
import com.mixer.api.exceptions.channel.ChannelNotFound;
import com.mixer.api.exceptions.interactive.MissingGameException;
import com.mixer.api.futures.SimpleFutureChecker;
import com.mixer.api.resource.interactive.RobotInfo;

public class Interactive {
    private static final int GAME_NOT_SET_RESPONSE = 403;

    public static class UnsetGameChecker extends SimpleFutureChecker<RobotInfo, BeamException> {
        public UnsetGameChecker() {
            super(ImmutableMap.<Integer, Class<? extends BeamException>>of(
                    GAME_NOT_SET_RESPONSE, MissingGameException.class,
                    404, ChannelNotFound.class,
                    401, UnAuthorizedException.class
                )
            );
        }
    }
}
