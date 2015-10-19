package pro.beam.api.resource.chat.events.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.chat.AbstractChatEvent;

public class IncomingWidgetData extends AbstractChatEvent.EventData {
    public int channel;
    public String id;
    @SerializedName("user_name") public String userName;
    @SerializedName("user_id") public int userId;
    @SerializedName("user_roles") public List<BeamUser.Role> userRoles;
    public String message;

    public String getMessage() {
        return message;
    }

}