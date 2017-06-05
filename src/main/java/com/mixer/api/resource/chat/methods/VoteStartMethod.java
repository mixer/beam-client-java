package com.mixer.api.resource.chat.methods;

import com.mixer.api.resource.chat.AbstractChatMethod;

public class VoteStartMethod extends AbstractChatMethod {
    /**
     * @param question The question
     * @param options  Possible answers
     * @param duration Duration of the poll, in seconds
     */
    public static VoteStartMethod from(String question, String[] options, int duration) {
        VoteStartMethod m = new VoteStartMethod();
        m.arguments = new Object[]{
                question,
                options,
                duration,
        };
        return m;
    }

    public VoteStartMethod() {
        super("vote:start");
    }

    public Object[] arguments;
}
