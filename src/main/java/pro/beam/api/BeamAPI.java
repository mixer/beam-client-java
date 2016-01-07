package pro.beam.api;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pro.beam.api.http.BeamHttpClient;
import pro.beam.api.services.AbstractBeamService;
import pro.beam.api.services.ServiceManager;
import pro.beam.api.services.impl.*;
import pro.beam.api.services.impl.UsersService;
import pro.beam.api.util.gson.InetSocketAddressAdapter;
import pro.beam.api.util.gson.URIAdapter;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.Executors;

public class BeamAPI {
    public final URI basePath;

    public final Gson gson;
    public final BeamHttpClient http;
    public final ListeningExecutorService executor;
    protected final ServiceManager services;

    public BeamAPI() {
        this(URI.create("https://beam.pro/api/v1/"), null, null);
    }

    public BeamAPI(URI basePath, String httpUsername, String httpPassword) {
        this.basePath = basePath;

        this.gson = new GsonBuilder()
                .registerTypeAdapter(InetSocketAddress.class, new InetSocketAddressAdapter())
                .registerTypeAdapter(URI.class, new URIAdapter())
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        this.executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        this.http = new BeamHttpClient(this, httpUsername, httpPassword);
        this.services = new ServiceManager();

        this.register(new UsersService(this));
        this.register(new ChatService(this));
        this.register(new EmotesService(this));
        this.register(new ChannelsService(this));
        this.register(new TypesService(this));
        this.register(new TetrisService(this));
    }

    public <T extends AbstractBeamService> T use(Class<T> service) {
        return this.services.get(service);
    }

    public boolean register(AbstractBeamService service) {
        return this.services.register(service);
    }
}
