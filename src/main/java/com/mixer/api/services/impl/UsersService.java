package com.mixer.api.services.impl;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.mixer.api.MixerAPI;
import com.mixer.api.exceptions.MixerException;
import com.mixer.api.http.MixerHttpClient;
import com.mixer.api.resource.MixerUser;
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
}
