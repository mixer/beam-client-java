package pro.beam.api.resource.chat.methods;

import pro.beam.api.resource.chat.AbstractChatMethod;

import java.util.Arrays;
import java.util.List;

public class CastVoteMessage extends AbstractChatMethod {
    public static CastVoteMessage of(int selectionIndex) {
        CastVoteMessage cvm = new CastVoteMessage();
        cvm.arguments = Arrays.asList(selectionIndex);

        return cvm;
    }

    public CastVoteMessage() {
        super("vote");
    }

    public List<Integer> arguments;
}
