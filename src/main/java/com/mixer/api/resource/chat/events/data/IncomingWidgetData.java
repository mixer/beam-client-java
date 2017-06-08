package com.mixer.api.resource.chat.events.data;

import com.google.gson.annotations.SerializedName;
import com.mixer.api.resource.MixerUser;
import com.mixer.api.resource.chat.AbstractChatEvent;

import java.util.List;

public class IncomingWidgetData extends AbstractChatEvent.EventData {
    public int channel;
    public String id;
    @SerializedName("user_name") public String userName;
    @SerializedName("user_id") public int userId;
    @SerializedName("user_roles") public List<MixerUser.Role> userRoles;
    public String message;

    public String getMessage() {
        return message;
    }

}