package pro.beam.api.util.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.net.URI;

public class URIAdapter extends TypeAdapter<URI> {
    @Override public void write(JsonWriter w, URI u) throws IOException {
        w.value(u.toString());
    }

    @Override public URI read(JsonReader r) throws IOException {
        return URI.create(r.nextString());
    }
}
