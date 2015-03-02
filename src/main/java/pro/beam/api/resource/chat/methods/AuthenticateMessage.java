package pro.beam.api.resource.chat.methods;

import pro.beam.api.resource.BeamChannel;
import pro.beam.api.resource.BeamUser;

public class AuthenticateMessage extends ChatMessageDatagram {
    private static final String METHOD_TOKEN = "auth";

    public static AuthenticateMessage from(BeamChannel channel, BeamUser user, String authkey) {
        AuthenticateMessage am = new AuthenticateMessage();
        am.method = METHOD_TOKEN;
        am.arguments = new Object[] {
            channel.id,
            user.id,
            authkey
        };

        return am;
    }

    public AuthenticateMessage() {
        this.type = Type.METHOD;
    }

    public Object[] arguments;
}
