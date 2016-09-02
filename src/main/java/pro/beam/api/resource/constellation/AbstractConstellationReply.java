package pro.beam.api.resource.constellation;

public abstract class AbstractConstellationReply extends AbstractConstellationDatagram {
    public String error;
    public int id;

    public AbstractConstellationReply() {
        this.type = Type.REPLY;
    }
}
