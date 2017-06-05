package com.mixer.api.resource.chat.methods;

import com.mixer.api.resource.BeamUser;
import com.mixer.api.resource.channel.BeamChannel;
import com.mixer.api.resource.chat.AbstractChatMethod;

public class AuthenticateMessage extends AbstractChatMethod {
    public static AuthenticateMessage from(BeamChannel channel, BeamUser user, String authkey) {
        AuthenticateMessage am = new AuthenticateMessage();
        am.arguments = new Object[] {
            channel.id,
            user != null ? user.id : null,
            authkey,
        };

        return am;
    }

    public AuthenticateMessage() {
        super("auth");
    }

    public Object[] arguments;
}
