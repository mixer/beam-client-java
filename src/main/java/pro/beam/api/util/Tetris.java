package pro.beam.api.util;

import com.google.common.base.Function;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.apache.http.client.HttpResponseException;
import pro.beam.api.exceptions.BeamException;
import pro.beam.api.exceptions.tetris.MissingGameException;
import pro.beam.api.resource.tetris.RobotInfo;

public class Tetris {
    private static final int GAME_UNSET_CODE = 403;

    public static CheckedFuture<RobotInfo, BeamException> checkFutureRobotInfo(ListenableFuture<RobotInfo> future) {
        return Futures.makeChecked(future, TETRIS_EXCEPTION_TRANSFORMER);
    }

    private static final Function<Exception, BeamException> TETRIS_EXCEPTION_TRANSFORMER = new Function<Exception, BeamException>() {
        @Override public BeamException apply(Exception e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof HttpResponseException)) {
                return null;
            }

            HttpResponseException hre = (HttpResponseException) cause;
            int status = hre.getStatusCode();

            if (status == GAME_UNSET_CODE) {
                return new MissingGameException();
            }

            return null;
        }
    };
}
