package pro.beam.api.util;

import com.google.common.base.Function;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.apache.http.client.HttpResponseException;
import pro.beam.api.exceptions.BeamException;
import pro.beam.api.exceptions.user.TwoFactorWrongCodeException;
import pro.beam.api.exceptions.user.WrongPasswordException;
import pro.beam.api.resource.BeamUser;

public class Users {
    private static final int WRONG_PASSWORD_RESPONSE = 401;
    private static final int TWOFACTOR_WRONG_CODE_RESPONSE = 499;

    public static CheckedFuture<BeamUser, BeamException> checkFutureUser(ListenableFuture<BeamUser> future) {
        return Futures.makeChecked(future, AUTH_EXCEPTION_TRANSFORMER);
    }

    private static final Function<Exception, BeamException> AUTH_EXCEPTION_TRANSFORMER = new Function<Exception, BeamException>() {
        @Override public BeamException apply(Exception e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof HttpResponseException)) {
                return null;
            }

            HttpResponseException hre = (HttpResponseException) cause;
            int status = hre.getStatusCode();

            switch (status) {
                case TWOFACTOR_WRONG_CODE_RESPONSE:
                    return new TwoFactorWrongCodeException();
                case WRONG_PASSWORD_RESPONSE:
                    return new WrongPasswordException();
                default:
                    return null;
            }
        }
    };
}
