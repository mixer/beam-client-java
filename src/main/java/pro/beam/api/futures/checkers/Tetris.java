package pro.beam.api.futures.checkers;

import com.google.common.collect.ImmutableMap;
import pro.beam.api.exceptions.BeamException;
import pro.beam.api.exceptions.tetris.MissingGameException;
import pro.beam.api.futures.SimpleFutureChecker;
import pro.beam.api.resource.tetris.RobotInfo;

public class Tetris {
    private static final int GAME_UNSET_CODE = 403;

    public static class UnsetGameChecker extends SimpleFutureChecker<RobotInfo, BeamException> {
        public UnsetGameChecker() {
            super(ImmutableMap.<Integer, Class<? extends BeamException>>of(
                    GAME_UNSET_CODE, MissingGameException.class
                )
            );
        }
    }
}
