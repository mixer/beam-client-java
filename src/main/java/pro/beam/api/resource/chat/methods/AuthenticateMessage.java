package pro.beam.api.resource.chat.methods;

import pro.beam.api.resource.BeamChannel;
import pro.beam.api.resource.BeamUser;

import java.util.Arrays;
import java.util.List;

public class AuthenticateMessage extends ChatMessageDatagram {
    private static final String METHOD_TOKEN = "auth";

    public static AuthenticateMessage from(BeamChannel channel, BeamUser user, String authkey) {
        AuthenticateMessage am = new AuthenticateMessage();
        am.method = METHOD_TOKEN;
        am.data = Arrays.asList(String.valueOf(channel.id),
                                String.valueOf(user.id),
                                authkey);

        return am;
    }

    public List<String> data;
}
