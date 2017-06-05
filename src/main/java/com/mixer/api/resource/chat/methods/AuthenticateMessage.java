package com.mixer.api.resource.chat.methods;

import com.mixer.api.resource.MixerUser;
import com.mixer.api.resource.channel.MixerChannel;
import com.mixer.api.resource.chat.AbstractChatMethod;

public class AuthenticateMessage extends AbstractChatMethod {
    public static AuthenticateMessage from(MixerChannel channel, MixerUser user, String authkey) {
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
