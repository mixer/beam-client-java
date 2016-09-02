package pro.beam.api.futures.checkers;

import com.google.common.collect.ImmutableMap;
import pro.beam.api.exceptions.BeamException;
import pro.beam.api.exceptions.Forbidden;
import pro.beam.api.futures.SimpleFutureChecker;
import pro.beam.api.resource.BeamUser;

public class JWT {
    private static final int UNAUTHORIZED_RESPONSE = 401;

    public static class JWTFutureChecker<T> extends SimpleFutureChecker<T, BeamException> {
        public JWTFutureChecker() {
            super(ImmutableMap.<Integer, Class<? extends BeamException>>of(
                    UNAUTHORIZED_RESPONSE, Forbidden.class
                )
            );
        }
    }
}
