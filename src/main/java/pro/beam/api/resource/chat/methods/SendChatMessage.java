package pro.beam.api.resource.chat.methods;

import java.util.Arrays;
import java.util.List;

public class SendChatMessage extends ChatMessageDatagram {
    private static final String METHOD_TOKEN = "msg";

    public static SendChatMessage of(String message) {
        SendChatMessage scm = new SendChatMessage();
        scm.method = METHOD_TOKEN;
        scm.arguments = Arrays.asList(message);

        return scm;
    }

    public List<String> arguments;
}
