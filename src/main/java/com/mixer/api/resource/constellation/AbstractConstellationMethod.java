package com.mixer.api.resource.constellation;

import java.util.Random;

public abstract class AbstractConstellationMethod extends AbstractConstellationDatagram {
    public String method;
    public int id;

    public AbstractConstellationMethod() {
        this(nextId(), null);
    }

    public AbstractConstellationMethod(int id) {
        this(id, null);
    }

    public AbstractConstellationMethod(String method) {
        this(nextId(), method);
    }

    public AbstractConstellationMethod(int id, String method) {
        this.id = id;

        this.method = method;
        this.type = Type.METHOD;
    }


    protected static final Random ID_RANDOM = new Random();
    protected static int nextId() {
        return Math.abs(ID_RANDOM.nextInt());
    }
}
