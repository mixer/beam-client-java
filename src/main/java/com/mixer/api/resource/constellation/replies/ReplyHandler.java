package com.mixer.api.resource.constellation.replies;

import com.google.common.util.concurrent.FutureCallback;
import com.mixer.api.resource.constellation.AbstractConstellationReply;

public abstract class ReplyHandler<T extends AbstractConstellationReply> implements FutureCallback<T> {
    @Override public void onFailure(Throwable err) {
        err.printStackTrace();
    }
}
