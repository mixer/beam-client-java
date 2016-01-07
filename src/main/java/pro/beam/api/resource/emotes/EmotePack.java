package pro.beam.api.resource.emotes;

import com.google.gson.annotations.SerializedName;
import pro.beam.api.response.emotes.EmoteSet;

import java.util.List;

public class EmotePack {
    public String name;
    public @SerializedName("default") boolean isDefault;
    public List<String> authors;
    public EmoteSet emoticons;
}
