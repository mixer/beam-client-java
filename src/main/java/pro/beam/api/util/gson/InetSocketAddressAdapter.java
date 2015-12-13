package pro.beam.api.util.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.net.InetSocketAddress;

public class InetSocketAddressAdapter extends TypeAdapter<InetSocketAddress> {
    @Override public void write(JsonWriter w, InetSocketAddress a) throws IOException {
        w.value(a.toString());
    }

    @Override
    public InetSocketAddress read(JsonReader r) throws IOException {
        String s = r.nextString();

        return new InetSocketAddress(
                s.substring(0, s.indexOf(':')),
                Integer.parseInt(s.substring(s.indexOf(':') + 1))
        );
    }
}
