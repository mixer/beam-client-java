package com.mixer.api.futures.checkers;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.mixer.api.exceptions.MixerException;
import com.mixer.api.exceptions.user.*;
import com.mixer.api.futures.JojenFutureChecker;
import com.mixer.api.futures.SimpleFutureChecker;
import com.mixer.api.resource.MixerUser;

public class Users {
    private static final int WRONG_PASSWORD_RESPONSE = 401;
    private static final int TWOFACTOR_WRONG_CODE_RESPONSE = 499;

    public static class TwoFactorFutureChecker extends SimpleFutureChecker<MixerUser, MixerException> {
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

    public static class ForgotPasswordChecker extends SimpleFutureChecker<String, MixerException> {
        public ForgotPasswordChecker() {
            super(ImmutableMap.of(
                    MISSING_USER_CODE, MissingUserException.class,
                    TOO_MANY_REQUESTS, TooManyResetsException.class
                )
            );
        }
    }

    private static final int BAD_REQUEST_CODE = 400;

    public static class ResetPasswordChecker extends SimpleFutureChecker<String, MixerException> {
        public ResetPasswordChecker() {
            super(ImmutableMap.of(
                    MISSING_USER_CODE, MissingUserException.class,
                    BAD_REQUEST_CODE, InvalidResetException.class
                )
            );
        }
    }

    public static class RegistrationChecker extends JojenFutureChecker<MixerUser> {
        public RegistrationChecker(Gson gson) {
            super(gson);
        }
    }
}
