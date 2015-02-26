package pro.beam.api;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pro.beam.api.http.BeamHttpClient;
import pro.beam.api.services.AbstractBeamService;
import pro.beam.api.services.ServiceManager;
import pro.beam.api.services.impl.UsersService;

import java.net.URI;
import java.util.concurrent.Executors;

public class BeamAPI {
    public static final URI BASE_PATH = URI.create("https://beam.pro/api/v1/");

    public final Gson gson = new GsonBuilder().create();
    public final BeamHttpClient http;
    public final ListeningExecutorService executor;
    protected final ServiceManager services;

    public BeamAPI() {
        this.executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        this.http = new BeamHttpClient(this);
        this.services = new ServiceManager();

        this.register(new UsersService(this));
    }

    public <T extends AbstractBeamService> T get(Class<T> service) {
        return this.services.get(service);
    }

    public boolean register(AbstractBeamService service) {
        return this.services.register(service);
    }
}
