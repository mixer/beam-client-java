package pro.beam.api.resource;

import java.io.Serializable;
import java.util.Date;

public abstract class Timeable implements Serializable {
    public Date createdAt;
    public Date updatedAt;
}
