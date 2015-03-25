package pro.beam.api.resource.chat.replies;

import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.chat.AbstractChatReply;

import java.util.ArrayList;

public class AuthenticationReply extends AbstractChatReply {
    public boolean authenticated;
    public ArrayList<BeamUser.Role> roles;
}
