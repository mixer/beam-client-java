package com.mixer.api.services.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.mixer.api.MixerAPI;
import com.mixer.api.exceptions.MixerException;
import com.mixer.api.exceptions.validation.ValidationError;
import com.mixer.api.futures.checkers.Users;
import com.mixer.api.http.MixerHttpClient;
import com.mixer.api.resource.MixerUser;
import com.mixer.api.resource.user.PasswordScore;
import com.mixer.api.response.users.UserFollowsResponse;
import com.mixer.api.response.users.UserSearchResponse;
import com.mixer.api.services.AbstractHTTPService;

import java.util.Map;

public class UsersService extends AbstractHTTPService {
    public UsersService(MixerAPI mixer) {
        super(mixer, "users");
    }

    public ListenableFuture<MixerUser> findOne(int id) {
        return this.get(String.valueOf(id), MixerUser.class);
    }

    public ListenableFuture<MixerUser> refresh() {
        return this.post("current/refresh", MixerUser.class);
    }

    public ListenableFuture<MixerUser> getCurrent() { return this.get("current", MixerUser.class); }

    /**
     * This AsyncFunction requests a JWT after logging in.
     */
    private AsyncFunction<MixerUser, MixerUser> jwtHandler = new AsyncFunction<MixerUser, MixerUser>() {
        @Override
        public CheckedFuture<MixerUser, MixerException> apply(final MixerUser mixerUser) throws Exception {
            return UsersService.this.mixer.use(JWTService.class).authorize(mixerUser);
        }
    };



    /**
     * Login without two factor authentication
     * @param username The user's username
     * @param password The user's password
     * @return
     */
    public CheckedFuture<MixerUser, MixerException> login(String username, String password) {
        return new Users.TwoFactorFutureChecker().check(Futures.transform(
                this.post("login", MixerUser.class, new ImmutableMap.Builder<String, Object>()
                        .put("username", username)
                        .put("password", password).build())
        , jwtHandler));
    }

    /**
     * Login with two factor authentication.
     *
     * @param username The user's username
     * @param password The user's password
     * @param authCode The user's two factor authentication code
     * @return
     */
    public CheckedFuture<MixerUser, MixerException> login(String username, String password, String authCode) {
        if (authCode.length() != 6) {
            throw new IllegalArgumentException("two factor authentication code have to be 6 digits (was " + authCode.length() + ")");
        } else {
            return new Users.TwoFactorFutureChecker().check(Futures.transform(
                this.post("login", MixerUser.class, new ImmutableMap.Builder<String, Object>()
                        .put("username", username)
                        .put("password", password)
                        .put("code", authCode).build())
            , jwtHandler));
        }
    }

    public ListenableFuture<String> logout() {
        this.http.clearJWT();
        return this.delete("current", null);
    }

    public ListenableFuture<UserSearchResponse> search(String query) {
        if (query != null && query.length() < 3) {
            throw new IllegalArgumentException("unable to preform search with query less than 3 characters (was "+query.length()+")");
        } else {
            Map<String, Object> args = MixerHttpClient.getArgumentsBuilder().put("query", query).build();

            return this.get("search", UserSearchResponse.class, args);
        }
    }

    public ListenableFuture<UserFollowsResponse> following(MixerUser user, int page, int limit) {
        return this.get(user.id + "/follows",
                         UserFollowsResponse.class,
                         MixerHttpClient.getArgumentsBuilder()
                                 .put("page", Math.max(0, page))
                                 .put("limit", Math.min(limit, 50))
                                 .put("noCount", 1).build());
    }

    public CheckedFuture<String, MixerException> forgotPassword(MixerUser user) {
        return new Users.ForgotPasswordChecker().check(
            this.post(
                "reset",
                String.class,
                MixerHttpClient.getArgumentsBuilder().put("email", user.email).build()
            )
        );
    }

    public CheckedFuture<String, MixerException> resetPassword(String token, String password) {
        Map<String, Object> args = MixerHttpClient.getArgumentsBuilder()
                .put("token", token)
                .put("password", password)
                .build();

        return new Users.ResetPasswordChecker().check(
                this.patch("reset", String.class, args)
        );
    }

    public CheckedFuture<MixerUser, ValidationError> register(String username, String password, String email) {
        return new Users.RegistrationChecker(this.mixer.gson).check(
            this.post(
                "",
                MixerUser.class,
                MixerHttpClient.getArgumentsBuilder()
                    .put("username", username)
                    .put("password", password)
                    .put("email", email)
                    .build()
            )
        );
    }

    public ListenableFuture<MixerUser> confirm(MixerUser user, String confirmationToken) {
        return this.patch(
                String.format("%d/confirm", user.id),
                MixerUser.class,
                MixerHttpClient.getArgumentsBuilder()
                        .put("id", user.id)
                        .put("code", confirmationToken)
                        .build()
        );
    }

    public ListenableFuture<PasswordScore> scorePassword(String password) {
        return this.post("passwordstr", PasswordScore.class, MixerHttpClient.getArgumentsBuilder()
            .put("password", password).build()
        );
    }
}
