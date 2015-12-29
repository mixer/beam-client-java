package pro.beam.api.resource.chat.events.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageComponent {
    public MessageMeta meta;
    public List<MessageTextComponent> message;

    public boolean isWhisper() {
        return this.meta.whisper;
    }

    public static class MessageMeta {
        public boolean me;
        public boolean whisper;
    }

    public static class MessageTextComponent {
        public Type type;
        public String data;
        public String text;

        // Emoticon-related
        public String source;
        public String pack;
        public Coords coords;

        // Link-related
        public String url;

        public static class Coords {
            public int x;
            public int y;
        }

        public enum Type {
            @SerializedName("text")TEXT,
            @SerializedName("emoticon")EMOTICON,
            @SerializedName("link")LINK,
        }
    }
}
