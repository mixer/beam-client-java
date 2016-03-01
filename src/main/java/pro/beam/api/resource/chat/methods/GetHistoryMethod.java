package pro.beam.api.resource.chat.methods;

import com.google.common.collect.ImmutableList;
import pro.beam.api.resource.chat.AbstractChatMethod;

import java.util.List;

public class GetHistoryMethod extends AbstractChatMethod {
    public List<Object> arguments;
    private GetHistoryMethod(int count) {
        super("history");

        this.arguments = ImmutableList.<Object>of(count);
    }

    public static GetHistoryMethod forCount(int count) {
        return new GetHistoryMethod(count);
    }
}
