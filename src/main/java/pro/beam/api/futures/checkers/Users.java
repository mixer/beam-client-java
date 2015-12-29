package pro.beam.api.futures.checkers;

import com.google.common.collect.ImmutableMap;
import pro.beam.api.exceptions.BeamException;
import pro.beam.api.exceptions.user.TwoFactorWrongCodeException;
import pro.beam.api.exceptions.user.WrongPasswordException;
import pro.beam.api.futures.SimpleFutureChecker;
import pro.beam.api.resource.BeamUser;

public class Users {
    private static final int WRONG_PASSWORD_RESPONSE = 401;
    private static final int TWOFACTOR_WRONG_CODE_RESPONSE = 499;

    public static class TwoFactorFutureChecker extends SimpleFutureChecker<BeamUser, BeamException> {
        public TwoFactorFutureChecker() {
            super(ImmutableMap.of(
                    WRONG_PASSWORD_RESPONSE, WrongPasswordException.class,
                    TWOFACTOR_WRONG_CODE_RESPONSE, TwoFactorWrongCodeException.class
                )
            );
        }
    }
}
