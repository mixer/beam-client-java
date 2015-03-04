package pro.beam.api.resource.chat.methods;

import pro.beam.api.resource.channel.BeamChannel;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.chat.AbstractChatMethod;

public class AuthenticateMessage extends AbstractChatMethod {
    public static AuthenticateMessage from(BeamChannel channel, BeamUser user, String authkey) {
        AuthenticateMessage am = new AuthenticateMessage();
        am.arguments = new Object[] {
            channel.id,
            user.id,
            authkey
        };

        return am;
    }

    public AuthenticateMessage() {
        super("auth");
    }

    public Object[] arguments;
}
