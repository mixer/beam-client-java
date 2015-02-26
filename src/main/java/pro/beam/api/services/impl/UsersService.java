package pro.beam.api.services.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.response.users.UserSearchResponse;
import pro.beam.api.services.AbstractHTTPService;

import java.util.Map;

public class UsersService extends AbstractHTTPService {
    public UsersService(BeamAPI beam) {
        super(beam, "users");
    }

    public ListenableFuture<UserSearchResponse> search(String query) {
        if (query != null && query.length() < 3) {
            throw new IllegalArgumentException("unable to preform search with query less than 3 characters (was "+query.length()+")");
        } else {
            Map<String, Object> args = new ImmutableMap.Builder<String, Object>()
                                      .put("query", query).build();

            return this.get("search", UserSearchResponse.class, args);
        }
    }
}
