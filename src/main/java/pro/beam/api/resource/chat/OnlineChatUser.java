package pro.beam.api.resource.chat;

import pro.beam.api.resource.BeamUser;

import java.util.List;

public class OnlineChatUser {
    public String userName;
    public List<BeamUser.Role> userRoles;
    public int userId;
}
