package pro.beam.api.http;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import pro.beam.api.BeamAPI;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;

public class BeamHttpClient {
    protected final BeamAPI beam;
    protected final HttpRequestFactory requestFactory;

    public BeamHttpClient(BeamAPI beam) {
        this.beam = beam;
        this.requestFactory = new NetHttpTransport().createRequestFactory(new HttpRequestInitializer() {
            @Override public void initialize(HttpRequest request) throws IOException {
                request.setParser(new JsonObjectParser(new GsonFactory()));
            }
        });
    }

    public <T> ListenableFuture<T> get(String path, Class<T> type, Map<String, Object> args) {
        return this.executor().submit(this.makeCallable(this.makeRequest(RequestType.GET,
                                                                         this.buildFromRelativePath(path, args)), type));
    }

    public <T> ListenableFuture<T> post(String path, Class<T> type, Object... args) {
        return this.executor().submit(this.makeCallable(this.makeRequest(RequestType.POST, path, args), type));
    }

    public <T> ListenableFuture<T> put(String path, Class<T> type, Object... args) {
        return this.executor().submit(this.makeCallable(this.makeRequest(RequestType.PUT, path, args), type));
    }

    public <T> ListenableFuture<T> delete(String path, Class<T> type) {
        return this.executor().submit(this.makeCallable(this.makeRequest(RequestType.DELETE, path), type));
    }

    private HttpRequest makeRequest(RequestType requestType, String path, Object... args) {
        return this.makeRequest(requestType, this.buildFromRelativePath(path, null), args);
    }

    /**
     * Creates a {@link com.google.api.client.http.HttpRequest} given a type, path, and optional content.
     *
     * @param requestType The type of HTTP/1.1 request to make (see {@link pro.beam.api.http.RequestType}
     *                    for more details.
     * @param url The URL to request content from.
     * @param args The content of the request.  This parameter is optional and considered to be nullable.
     *
     * @return A request built from the specification above.
     */
    private HttpRequest makeRequest(RequestType requestType, GenericUrl url, Object... args) {
        try {
            return this.requestFactory.buildRequest(requestType.name(),
                   url,
                   this.makeRequestContent(args));
        } catch (IOException e) {
            return null;
        }
    }

    private HttpContent makeRequestContent(Object... args) {
        if (args == null || args.length == 0) {
            return null;
        } else {
            byte[] contents = this.beam.gson.toJson(args.length == 1 ? args[0] : args).getBytes();
            return new ByteArrayContent("application/json", contents);
        }
    }

    /**
     * This public-facing method constructs a callable which has the responsibility of executing the request
     * given, and parsing it using an instance of a GSON parser into the desired class-type,  Typically this method
     * is used to submit a {@link java.util.concurrent.Callable} to the
     * {@link com.google.common.util.concurrent.ListeningExecutorService} such that a callback can be fired when the
     * request and parsing is complete.
     *
     * @param request The {@link com.google.api.client.http.HttpRequest} to be executed.
     * @param type The class-type of the response body.  (See the "response" package for examples.)
     *
     * @return The callable as described above.
     */
    private <T> Callable<T> makeCallable(final HttpRequest request, final Class<T> type) {
        return new Callable<T>() {
            @Override public T call() throws Exception {
                HttpResponse response = request.execute();
                return BeamHttpClient.this.beam.gson.fromJson(response.parseAsString(), type);
            }
        };
    }

    /**
     * Takes a relative path and makes it absolute with respect to the root of the API.
     *
     * @param path The relative path (ex., "channels/")
     *
     * @return The absolute resolved path as a GenericUrl.
     */
    private GenericUrl buildFromRelativePath(String path, Map<String, Object> args) {
        GenericUrl url = new GenericUrl(BeamAPI.BASE_PATH.resolve(path));
        if (args != null) {
            for (Map.Entry<String, Object> entry : args.entrySet()) {
                url.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        return url;
    }

    private ListeningExecutorService executor() {
        return this.beam.executor;
    }
}
