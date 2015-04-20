package pro.beam.api.resource.chat.events.data;

import ca.ifdev.utils.Emotes;
import ca.ifdev.utils.Prefixes;
import ca.ifdev.utils.Self;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterators;
import com.google.gson.annotations.SerializedName;

import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.chat.AbstractChatEvent;

import java.util.List;

public class IncomingWidgetData extends AbstractChatEvent.EventData {
    public int channel;
    public String id;
    public String user_name;
    public String user_id;
    public List<BeamUser.Role> user_roles;
    public String message;

    public String getMessage() {
    	return message;
    }

}