package pro.beam.api.services.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.api.exceptions.BeamException;
import pro.beam.api.futures.checkers.JWT;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.services.AbstractHTTPService;

/**
 * Implements a JWT service.
 */
public class JWTService extends AbstractHTTPService {
    public JWTService(BeamAPI beam) {
        super(beam, "jwt");
    }

    /**
     * Retrieve a JWT from the api. When passed in a BeamUser use this as the result.
     * @return
     */
    public CheckedFuture<BeamUser, BeamException> authorize(final BeamUser beamUser) {
        return new JWT.JWTFutureChecker<BeamUser>().check(Futures.transform(
            this.post("authorize", null, new Object()),
            new AsyncFunction<Object, BeamUser>() {
                @Override
                public ListenableFuture<BeamUser> apply(Object o) throws Exception {
                    return Futures.immediateCheckedFuture(beamUser);
                }
            }
        ));
    }
}
