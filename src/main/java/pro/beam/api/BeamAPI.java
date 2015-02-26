package pro.beam.api;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.util.concurrent.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class BeamAPI {
    public static final ListeningExecutorService EXECUTOR_SERVICE = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    public static final URI BASE_PATH = URI.create("https://beam.pro/api/v1/");
    protected final HttpRequestFactory requestFactory;
    protected final Gson gson = new GsonBuilder().create();

    public BeamAPI() {
        this.requestFactory = new NetHttpTransport().createRequestFactory();
    }

    public <T> ListenableFuture<T> get(String path, final Class<T> type) throws IOException {
        final HttpRequest request = this.requestFactory.buildGetRequest(this.buildFromRelativePath(path));

        return EXECUTOR_SERVICE.submit(new Callable<T>() {
            @Override public T call() throws Exception {
                HttpResponse response = request.execute();
                return BeamAPI.this.gson.fromJson(response.parseAsString(), type);
            }
        });
    }

    private GenericUrl buildFromRelativePath(String path) {
        return new GenericUrl(BASE_PATH.resolve(path));
    }
}
