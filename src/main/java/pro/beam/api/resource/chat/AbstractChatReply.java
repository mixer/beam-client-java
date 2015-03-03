package pro.beam.api.resource.chat;

public abstract class AbstractChatReply extends AbstractChatDatagram {
    public String error;
    public int id;

    public AbstractChatReply() {
        this.type = Type.REPLY;
    }
}
