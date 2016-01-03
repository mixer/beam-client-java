package pro.beam.api.resource.chat;

import java.util.Random;

public abstract class AbstractChatMethod extends AbstractChatDatagram {
    public String method;
    public int id;

    public AbstractChatMethod() {
        this(nextId(), null);
    }

    public AbstractChatMethod(int id) {
        this(id, null);
    }

    public AbstractChatMethod(String method) {
        this(nextId(), method);
    }

    public AbstractChatMethod(int id, String method) {
        this.id = id;

        this.method = method;
        this.type = Type.METHOD;
    }


    protected static final Random ID_RANDOM = new Random();
    protected static int nextId() {
        return ID_RANDOM.nextInt();
    }
}
