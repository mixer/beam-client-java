package pro.beam.api.futures.checkers;

import com.google.common.collect.ImmutableMap;
import pro.beam.api.exceptions.BeamException;
import pro.beam.api.exceptions.user.*;
import pro.beam.api.futures.SimpleFutureChecker;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.user.validation.UserValidationException;

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

    private static final int MISSING_USER_CODE = 404;
    private static final int TOO_MANY_REQUESTS = 429;

    public static class ForgotPasswordChecker extends SimpleFutureChecker<String, BeamException> {
        public ForgotPasswordChecker() {
            super(ImmutableMap.of(
                    MISSING_USER_CODE, MissingUserException.class,
                    TOO_MANY_REQUESTS, TooManyResetsException.class
                )
            );
        }
    }

    private static final int BAD_REQUEST_CODE = 400;

    public static class ResetPasswordChecker extends SimpleFutureChecker<String, BeamException> {
        public ResetPasswordChecker() {
            super(ImmutableMap.of(
                    MISSING_USER_CODE, MissingUserException.class,
                    BAD_REQUEST_CODE, InvalidResetException.class
                )
            );
        }
    }

    // TODO(taylor): expand to support custom validation errors when the API is standardized
    public static class RegistrationChecker extends SimpleFutureChecker<BeamUser, UserValidationException> {
        public RegistrationChecker() {
            super(ImmutableMap.<Integer, Class<? extends UserValidationException>>of());
        }
    }
}
