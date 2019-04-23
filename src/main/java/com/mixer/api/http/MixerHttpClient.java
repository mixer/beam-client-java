package com.mixer.api.http;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.BaseEncoding;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.mixer.api.MixerAPI;
import com.mixer.api.resource.user.JSONWebToken;
import com.mixer.api.services.impl.JWTService;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class MixerHttpClient {
    private static final String PROXY_HOST_PROP = "http.proxyHost";
    private static final String PROXY_PORT_PROP = "http.proxyPort";

    public final CookieStore cookieStore;
    public final HttpClient http;

    protected final MixerAPI mixer;
    protected final HttpClientContext context = null;

    private String userAgent;
    private String oauthToken;
    private String clientId;

    public static final String X_JWT_HEADER = "X-JWT";
    private JSONWebToken jwt;
    private String jwtString;
    private boolean renewJwt;

    public static final String CSRF_TOKEN_HEADER = "x-csrf-token";
    public static final int CSRF_STATUS_CODE = 461;
    private String csrfToken;

    public MixerHttpClient(MixerAPI mixer, String clientId) {
        this(mixer, null, clientId);
    }

    public MixerHttpClient(MixerAPI mixer, String oauthToken, String clientId) {
        this.mixer = mixer;
        this.cookieStore = new BasicCookieStore();
        this.http = this.buildHttpClient();

        if (oauthToken != null) {
            this.oauthToken = oauthToken;
        }

        this.clientId = clientId;
    }

    protected HttpClient buildHttpClient() {
        String proxyHost = System.getProperty(PROXY_HOST_PROP);
        String proxyPort = System.getProperty(PROXY_PORT_PROP);

        HttpClientBuilder builder = HttpClientBuilder.create().setDefaultCookieStore(this.cookieStore);

        if (proxyHost != null && proxyPort != null) {
            builder.setProxy(new HttpHost(proxyHost, Integer.parseInt(proxyPort)));
        }

        return builder.build();
    }

    /**
     * Called for each method. Used to do pre-flight checks, like renewing jwt's.
     * @param callable
     * @param <T>
     * @return
     */
    private <T> ListenableFuture<T> baseSubmit(final Callable<T> callable) {
        if (shouldRenewJWT()) {
            return Futures.transform(this.mixer.use(JWTService.class).authorize(new Object()), new AsyncFunction<Object, T>() {
                @Override
                public ListenableFuture<T> apply(Object o) throws Exception {
                    MixerHttpClient.this.renewJwt = false;
                    return MixerHttpClient.this.executor().submit(callable);
                }
            });
        }
        return MixerHttpClient.this.executor().submit(callable);
    }


    public <T> ListenableFuture<T> get(String path, Class<T> type, Map<String, Object> args) {
        return this.baseSubmit(this.makeCallable(this.makeRequest(RequestType.GET, this.buildFromRelativePath(path, args)), type));
    }

    public <T> ListenableFuture<T> post(String path, Class<T> type, Object... args) {
        return this.baseSubmit(this.makeCallable(this.makeRequest(RequestType.POST, path, args), type));
    }

    public <T> ListenableFuture<T> put(String path, Class<T> type, Object... args) {
        return this.baseSubmit(this.makeCallable(this.makeRequest(RequestType.PUT, path, args), type));
    }

    public <T> ListenableFuture<T> patch(String path, Class<T> type, Object... args) {
        return this.baseSubmit(this.makeCallable(this.makeRequest(RequestType.PATCH, path, args), type));
    }

    public <T> ListenableFuture<T> delete(String path, Class<T> type, Object... args) {
        return this.baseSubmit(this.makeCallable(this.makeRequest(RequestType.DELETE, path, args), type));
    }

    private HttpUriRequest makeRequest(RequestType requestType, String path, Object... args) {
        return this.makeRequest(requestType, this.buildFromRelativePath(path, null), args);
    }

    /**
     * Creates a {@link org.apache.http.HttpRequest} given a type, path, and optional content.
     *
     * @param requestType The type of HTTP/1.1 request to make (see {@link RequestType}
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
            requestBuilder.addHeader("Authorization", "JWT " + this.jwtString);
        }

        if (this.oauthToken == null && this.jwt == null) {
            requestBuilder.addHeader("Client-Id", clientId);
        }
        if (this.csrfToken != null) {
            requestBuilder.addHeader(CSRF_TOKEN_HEADER, this.csrfToken);
        }

        return requestBuilder.build();
    }

    private HttpEntity makeEntity(Object... args) {
        Object object = args.length == 1 ? args[0] : args;
        String serialized = this.mixer.gson.toJson(object);

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
                MixerHttpClient self = MixerHttpClient.this;
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
        if (type != null && completeResponse.body != null) {
        	String result = completeResponse.body;
        	result = result.replaceAll("[+|-][0-1][0-9]:[0-5][0-9]", ".000Z");
            return this.mixer.gson.fromJson(result, type);
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
        Header[] jwtHeaders = partialResponse.getHeaders(X_JWT_HEADER);
        if (jwtHeaders.length > 0) {
            Header jwtHeader = jwtHeaders[0];
            this.jwtString = jwtHeader.getValue();
            this.jwt = parseJWTData(jwtHeader.getValue());
        }
    }

    /**
     * Returns if the jwt needs to be refreshed. If it should, set a boolean
     * that makes sure it's only renewed by one request.
     * @return
     */
    private boolean shouldRenewJWT() {
        if (!this.renewJwt && this.jwt != null && this.jwt.hasExpired()) {
            this.renewJwt = true;
            return true;
        }
        return false;
    }

    /**
     * Parses the actual JWT data out of the token.
     * @param jwtValue
     * @return
     */
    private JSONWebToken parseJWTData(String jwtValue) {
        String[] parts = jwtValue.split("\\.");
        if (parts.length != 3) {
            return null;
        }

        return this.mixer.gson.fromJson(
                new String(BaseEncoding.base64Url().decode(parts[1])),
                JSONWebToken.class);
    }

    /**
     * Takes a relative path and makes it absolute with respect to the root of the API.
     *
     * @param path The relative path (ex., "channels/")
     *
     * @return The absolute resolved path as a GenericUrl.
     */
    private URI buildFromRelativePath(String path, Map<String, Object> args) {
        URIBuilder builder = new URIBuilder(this.mixer.basePath.resolve(path));

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
        return this.mixer.executor;
    }

    protected String getUserAgent() {
        if (this.userAgent == null) {
            String version = Optional.fromNullable(MixerAPI.class.getPackage().getImplementationVersion()).or("unknown");
            String jdk = "Java: " + System.getProperty("java.version");
            String os = "OS: " + System.getProperty("os.name");

            this.userAgent = new StringBuilder("MixerClient/").append(version)
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

    public JSONWebToken getJwt() {
        return this.jwt;
    }

    public Map<String, String> getDefaultSocketHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        if (this.jwt != null) {
            headers.put("Authorization", "JWT " + jwtString);
        }
        headers.put("User-Agent", getUserAgent());

        return headers;
    }
}
