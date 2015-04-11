package pro.beam.api.http;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import pro.beam.api.BeamAPI;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.Callable;

public class BeamHttpClient {
    public final CookieStore cookieStore;
    protected final BeamAPI beam;
    public final HttpClient http;

    public BeamHttpClient(BeamAPI beam) {
        this.beam = beam;
        this.cookieStore = new BasicCookieStore();
        this.http = HttpClientBuilder.create().setDefaultCookieStore(this.cookieStore).build();
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

    public <T> ListenableFuture<T> patch(String path, Class<T> type, Object... args) {
        return this.executor().submit(this.makeCallable(this.makeRequest(RequestType.PATCH, path, args), type));
    }

    public <T> ListenableFuture<T> delete(String path, Class<T> type, Object... args) {
        return this.executor().submit(this.makeCallable(this.makeRequest(RequestType.DELETE, path, args), type));
    }

    private HttpUriRequest makeRequest(RequestType requestType, String path, Object... args) {
        return this.makeRequest(requestType, this.buildFromRelativePath(path, null), args);
    }

    /**
     * Creates a {@link org.apache.http.HttpRequest} given a type, path, and optional content.
     *
     * @param requestType The type of HTTP/1.1 request to make (see {@link pro.beam.api.http.RequestType}
     *                    for more details.
     * @param uri The URI to request content from.
     * @param args The content of the request.  This parameter is optional and considered to be nullable.
     *
     * @return A request built from the specification above.
     */
    private HttpUriRequest makeRequest(RequestType requestType, URI uri, Object... args) {
        return RequestBuilder.create(requestType.name())
                             .setUri(uri)
                             .setEntity(this.makeEntity(args)).build();
    }

    private HttpEntity makeEntity(Object... args) {
        Object object = args.length == 1 ? args[0] : args;
        return new ByteArrayEntity(this.beam.gson.toJson(object).getBytes(), ContentType.APPLICATION_JSON);
    }

    /**
     * This private-facing method constructs a callable which has the responsibility of executing the request
     * given, and parsing it using an instance of a GSON parser into the desired class-type,  Typically this method
     * is used to submit a {@link java.util.concurrent.Callable} to the
     * {@link com.google.common.util.concurrent.ListeningExecutorService} such that a callback can be fired when the
     * request and parsing is complete.
     *
     * @param request The {@link org.apache.http.HttpRequest} to be executed.
     * @param type The class-type of the response body.  (See the "response" package for examples.)
     *
     * @return The callable as described above.
     */
    private <T> Callable<T> makeCallable(final HttpUriRequest request, final Class<T> type) {
        return new Callable<T>() {
            @Override public T call() throws Exception {
                BeamHttpClient self = BeamHttpClient.this;
                String response = self.http.execute(request, new BasicResponseHandler());

                // Allow a null response to be given back, such that we return a ListenableFuture
                // with null.
                if (type != null) {
                    return self.beam.gson.fromJson(response, type);
                } else {
                    return null;
                }
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
    private URI buildFromRelativePath(String path, Map<String, Object> args) {
        URIBuilder builder = new URIBuilder(BeamAPI.BASE_PATH.resolve(path));

        if (args != null) {
            for (Map.Entry<String, Object> entry : args.entrySet()) {
                builder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        try {
            return builder.build();
        } catch (URISyntaxException e) {
            return null;
        }
    }

    private ListeningExecutorService executor() {
        return this.beam.executor;
    }

    public static ImmutableMap.Builder<String, Object> getArgumentsBuilder() {
        return new ImmutableMap.Builder<>();
    }
}
