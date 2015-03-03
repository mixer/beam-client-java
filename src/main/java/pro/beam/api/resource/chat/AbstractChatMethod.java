package pro.beam.api.resource.chat;

import java.util.Random;

public abstract class AbstractChatMethod extends AbstractChatDatagram {
    public String method;
    public int id;

    public AbstractChatMethod() {
        this(null);
    }

    public AbstractChatMethod(String method) {
        this.method = method;
        this.id = new Random().nextInt();
        this.type = Type.METHOD;
    }
}
