package pro.beam.api.resource.channel;

import java.util.Date;
import java.util.Map;

public class BeamResource {
    public int id;
    public Map<String, Object> meta;
    public int relid;
    public String remote_path;
    public String store;
    public String type;
    public String url;
    public Date createdAt;
    public Date updatedAt;
}
