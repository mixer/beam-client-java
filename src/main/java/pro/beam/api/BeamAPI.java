package pro.beam.api;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.util.concurrent.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pro.beam.api.http.RequestType;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class BeamAPI {
    public static final URI BASE_PATH = URI.create("https://beam.pro/api/v1/");

    protected final ListeningExecutorService executor;
    protected final HttpRequestFactory requestFactory;
    protected final Gson gson = new GsonBuilder().create();

    public BeamAPI() {
        this.executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        this.requestFactory = new NetHttpTransport().createRequestFactory();
    }

    public <T> ListenableFuture<T> get(String path, Class<T> type) {
        return this.executor.submit(this.makeCallable(this.makeRequest(RequestType.GET, path), type));
    }

    public <T> ListenableFuture<T> post(String path, Class<T> type, Object... args) {
        return this.executor.submit(this.makeCallable(this.makeRequest(RequestType.POST, path, args), type));
    }

    public <T> ListenableFuture<T> put(String path, Class<T> type, Object... args) {
        return this.executor.submit(this.makeCallable(this.makeRequest(RequestType.PUT, path, args), type));
    }

    public <T> ListenableFuture<T> delete(String path, Class<T> type) {
        return this.executor.submit(this.makeCallable(this.makeRequest(RequestType.DELETE, path), type));
    }

    /**
     * Forwards the method call to #makeRequest without any content.
     */
    private HttpRequest makeRequest(RequestType requestType, String path) {
        return this.makeRequest(requestType, path, null);
    }

    /**
     * Creates a {@link com.google.api.client.http.HttpRequest} given a type, path, and optional content.
     *
     * @param requestType The type of HTTP/1.1 request to make (see {@link pro.beam.api.http.RequestType}
     *                    for more details.
     * @param path The absolute path to direct the request at (see #buildFromRelativePath for more details)
     * @param args The content of the request.  This parameter is optional and considered to be nullable.
     *
     * @return A request built from the specification above.
     */
    private HttpRequest makeRequest(RequestType requestType, String path, Object... args) {
        try {
            return this.requestFactory.buildRequest(requestType.name(),
                                                    this.buildFromRelativePath(path),
                                                    this.makeRequestContent(args));
        } catch (IOException e) {
            return null;
        }
    }

    private HttpContent makeRequestContent(Object... args) {
        byte[] contents = this.gson.toJson(args.length == 1 ? args[0] : args).getBytes();
        return new ByteArrayContent("application/json", contents);
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
                return BeamAPI.this.gson.fromJson(response.parseAsString(), type);
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
    private GenericUrl buildFromRelativePath(String path) {
        return new GenericUrl(BASE_PATH.resolve(path));
    }
}
