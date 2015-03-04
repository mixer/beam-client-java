package pro.beam.api.resource.chat.replies;

import com.google.common.util.concurrent.FutureCallback;
import pro.beam.api.resource.chat.AbstractChatReply;

public abstract class ReplyHandler<T extends AbstractChatReply> implements FutureCallback<T> {
    @Override public void onFailure(Throwable err) {
        err.printStackTrace();
    }
}
