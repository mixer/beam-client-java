package pro.beam.api.resource.chat.methods;

import pro.beam.api.resource.chat.AbstractChatMethod;

import java.util.Arrays;
import java.util.List;

public class ChatSendMethod extends AbstractChatMethod {
    public static ChatSendMethod of(String message) {
        ChatSendMethod scm = new ChatSendMethod();
        scm.arguments = Arrays.asList(message);

        return scm;
    }

    public ChatSendMethod() {
        super("msg");
    }

    public List<String> arguments;
}
