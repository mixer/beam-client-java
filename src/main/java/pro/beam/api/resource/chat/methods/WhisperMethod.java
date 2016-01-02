package pro.beam.api.resource.chat.methods;

import com.google.common.collect.ImmutableList;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.chat.AbstractChatMethod;

import java.util.List;

public class WhisperMethod extends AbstractChatMethod {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        protected BeamUser to;
        protected String message;

        public Builder to(BeamUser u) {
            this.to = u;
            return this;
        }

        public Builder send(String message) {
            this.message = message;
            return this;
        }

        public WhisperMethod build(int id) {
            WhisperMethod method = new WhisperMethod(id);
            method.arguments = ImmutableList.of(this.to.username, this.message);

            return method;
        }

        public WhisperMethod build() {
            return this.build(AbstractChatMethod.nextId());
        }
    }

    public List<String> arguments;
    private WhisperMethod(int id) {
        super(id, "whisper");
    }
}
