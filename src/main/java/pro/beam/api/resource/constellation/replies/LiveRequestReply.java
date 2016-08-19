package pro.beam.api.resource.constellation.replies;

import com.google.gson.JsonObject;
import pro.beam.api.resource.constellation.AbstractConstellationReply;

public class LiveRequestReply extends AbstractConstellationReply {
    public JsonObject result;
    public ConstellationErrorReply error;
}
