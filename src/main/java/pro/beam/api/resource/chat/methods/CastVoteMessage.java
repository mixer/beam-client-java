package pro.beam.api.resource.chat.methods;

import java.util.Arrays;
import java.util.List;

public class CastVoteMessage extends ChatMessageDatagram {
    public static CastVoteMessage of(int selectionIndex) {
        CastVoteMessage cvm = new CastVoteMessage();
        cvm.method = "vote";
        cvm.arguments = Arrays.asList(selectionIndex);

        return cvm;
    }

    public List<Integer> arguments;
}
