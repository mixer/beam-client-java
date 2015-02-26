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

    public <T> ListenableFuture<T> get(String path, Class<T> type) throws IOException {
        return this.executor.submit(this.makeCallable(this.makeRequest(RequestType.GET, path), type));
    }

    private HttpRequest makeRequest(RequestType requestType, String path) throws IOException {
        return this.makeRequest(requestType, path, null);
    }

    private HttpRequest makeRequest(RequestType requestType, String path, HttpContent content) throws IOException {
        return this.requestFactory.buildRequest(requestType.name(),
                                                this.buildFromRelativePath(path),
                                                content);
    }

    private <T> Callable<T> makeCallable(final HttpRequest request, final Class<T> type) {
        return new Callable<T>() {
            @Override public T call() throws Exception {
                HttpResponse response = request.execute();
                return BeamAPI.this.gson.fromJson(response.parseAsString(), type);
            }
        };
    }

    private GenericUrl buildFromRelativePath(String path) {
        return new GenericUrl(BASE_PATH.resolve(path));
    }
}
