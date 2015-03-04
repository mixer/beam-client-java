package pro.beam.api.resource.chat.events.data;

import com.google.gson.annotations.SerializedName;
import pro.beam.api.resource.chat.AbstractChatEvent;

import java.util.List;

public class PollStartData extends AbstractChatEvent.EventData {
    @SerializedName("q") public String question;
    public List<String> answers;
    public int duration;
    public long endsAt;
}
