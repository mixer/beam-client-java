package com.mixer.api.resource.channel;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class MixerResource implements Serializable {
    public int id;
    public HashMap<String, Object> meta;
    public int relid;
    public String remotePath;
    public String store;
    public String type;
    public String url;
    public Date createdAt;
    public Date updatedAt;
}
