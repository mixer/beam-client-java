package pro.beam.api.http;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.gson.Gson;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.*;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.*;
import pro.beam.api.BeamAPI;
import pro.beam.api.resource.user.JSONWebToken;
import pro.beam.api.services.impl.UsersService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class BeamHttpClient {
    public final CookieStore cookieStore;
    public final HttpClient http;

    protected final BeamAPI beam;
    protected final HttpClientContext context;

    private String userAgent;
    private String oauthToken;

    private JSONWebToken jwt;
    private String jwtString;

    public static final String CSRF_TOKEN_HEADER = "x-csrf-token";
    public static final int CSRF_STATUS_CODE = 461;
    private String csrfToken;

    public BeamHttpClient(BeamAPI beam) {
        this(beam, null, null);
    }

    public BeamHttpClient(BeamAPI beam, String oauthToken) {
        this(beam, null, null, oauthToken);
    }

    public BeamHttpClient(BeamAPI beam, String httpUsername, String httpPassword) {
        this(beam, httpUsername, httpPassword, null);
    }

    public BeamHttpClient(BeamAPI beam, String httpUsername, String httpPassword, String oauthToken) {
        this.beam = beam;
        this.cookieStore = new BasicCookieStore();

        if (httpUsername != null && httpPassword != null) {
            this.context = HttpClientContext.create();

            AuthCache ac = new BasicAuthCache();
            ac.put(new HttpHost(this.beam.basePath.getHost()), new BasicScheme());
            this.context.setAuthCache(ac);

            CredentialsProvider cp = new BasicCredentialsProvider();
            cp.setCredentials(
                    AuthScope.ANY,
                    new UsernamePasswordCredentials(httpUsername, httpPassword)
            );
            this.context.setCredentialsProvider(cp);
        } else {
            this.context = null;
        }

        this.http = this.buildHttpClient();

        if (oauthToken != null) {
            this.oauthToken = oauthToken;
        }
    }

    protected HttpClient buildHttpClient() {
        return HttpClientBuilder.create().setDefaultCookieStore(this.cookieStore).build();
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
        RequestConfig.Builder config = RequestConfig.copy(RequestConfig.DEFAULT);
        config.setCookieSpec(CookieSpecs.BEST_MATCH);

        RequestBuilder requestBuilder = RequestBuilder.create(requestType.name())
                             .setUri(uri)
                             .setConfig(config.build())
                             .setEntity(this.makeEntity(args))
                             .setHeader("User-Agent", this.getUserAgent());

        if (this.oauthToken != null) {
            requestBuilder.addHeader("Authorization", "Bearer " + this.oauthToken);
        }
        if (this.jwt != null) {
            if (this.jwt.hasExpired()) {
                // todo(jamydev): Renew JWT grant
            }
            requestBuilder.addHeader("Authorization", "JWT " + this.jwtString);
        }
        if (this.csrfToken != null) {
            requestBuilder.addHeader(CSRF_TOKEN_HEADER, this.csrfToken);
        }

        return requestBuilder.build();
    }

    private HttpEntity makeEntity(Object... args) {
        Object object = args.length == 1 ? args[0] : args;
        String serialized = this.beam.gson.toJson(object);

        try {
            StringEntity e = new StringEntity(serialized);
            e.setContentEncoding("UTF-8");
            e.setContentType("application/json");

            return e;
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        return null;
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
            @Override public T call() throws IOException, HttpBadResponseException {
                BeamHttpClient self = BeamHttpClient.this;
                try {
                    return handleRequest(request, type);
                } catch(HttpBadResponseException e) {
                    if (e.response.status.getStatusCode() == CSRF_STATUS_CODE) {
                        return handleRequest(request, type);
                    }
                    throw e;
                }
            }
        };
    }

    public <T> T handleRequest(HttpUriRequest request, Class<T> type) throws IOException, HttpBadResponseException {
        HttpResponse partialResponse = this.makeRequest(request);
        return this.handleResponse(partialResponse, type);
    }

    public HttpResponse makeRequest(HttpUriRequest request) throws IOException {
        return this.http.execute(request, this.context);
    }

    public <T> T handleResponse(HttpResponse partialResponse, Class<T> type) throws IOException, HttpBadResponseException {
        HttpCompleteResponse completeResponse = new HttpCompleteResponseHandler().handleResponse(partialResponse);

        this.handleCSRF(partialResponse);

        if (completeResponse.status.getStatusCode() >= 300) {
            throw new HttpBadResponseException(completeResponse);
        }

        this.handleJWT(partialResponse);

        // Allow a null response to be given back, such that we return a ListenableFuture
        // with null.
        if (type != null) {
            return this.beam.gson.fromJson(completeResponse.body(), type);
        } else {
            return null;
        }
    }

    /**
     * Given a HTTP Response, check to see if a CSRF token is present, If it is we store this in the Client
     * for future requests.
     */
    private void handleCSRF(HttpResponse response) {
        if (response.containsHeader(CSRF_TOKEN_HEADER)) {
            this.csrfToken = response.getHeaders(CSRF_TOKEN_HEADER)[0].getValue();
        }
    }

    /**
     * Removes the current JWT from the client.
     */
    public void clearJWT() {
        this.jwt = null;
    }

    /**
     * Checks the response for an X-JWT header so we can parse it.
     * @param partialResponse
     */
    private void handleJWT(HttpResponse partialResponse) {
        Header[] jwtHeaders = partialResponse.getHeaders("X-JWT");
        if (jwtHeaders.length > 0) {
            Header jwtHeader = jwtHeaders[0];
            this.jwtString = jwtHeader.getValue();
            this.jwt = this.beam.gson.fromJson(jwtHeader.getValue(), JSONWebToken.class);
        }
    }

    /**
     * Takes a relative path and makes it absolute with respect to the root of the API.
     *
     * @param path The relative path (ex., "channels/")
     *
     * @return The absolute resolved path as a GenericUrl.
     */
    private URI buildFromRelativePath(String path, Map<String, Object> args) {
        URIBuilder builder = new URIBuilder(this.beam.basePath.resolve(path));

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

    protected String getUserAgent() {
        if (this.userAgent == null) {
            String version = Optional.fromNullable(BeamAPI.class.getPackage().getImplementationVersion()).or("unknown");
            String jdk = "Java: " + System.getProperty("java.version");
            String os = "OS: " + System.getProperty("os.name");

            this.userAgent = new StringBuilder("BeamClient/").append(version)
                    .append(" (").append(Joiner.on(", ").join(jdk,os)).append(")")
                    .toString();
        }

        return this.userAgent;
    }
    public void setUserAgent(String agent) {
        this.userAgent = agent;
    }

    public static ImmutableMap.Builder<String, Object> getArgumentsBuilder() {
        return new ImmutableMap.Builder<>();
    }

    public String getCsrfToken() {
        return csrfToken;
    }

    public String getJWTString() {
        return this.jwtString;
    }

    public Map<String, String> getDefaultSocketHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "JWT " + jwtString);
        headers.put("User-Agent", getUserAgent());

        return headers;
    }
}
