package com.mixer.api;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mixer.api.http.MixerHttpClient;
import com.mixer.api.services.AbstractMixerService;
import com.mixer.api.services.ServiceManager;
import com.mixer.api.services.impl.*;
import com.mixer.api.util.gson.DateAdapter;
import com.mixer.api.util.gson.InetSocketAddressAdapter;
import com.mixer.api.util.gson.URIAdapter;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Date;
import java.util.concurrent.Executors;

public class MixerAPI {
    public final URI basePath;

    public final Gson gson;
    public final MixerHttpClient http;
    public final ListeningExecutorService executor;
    protected final ServiceManager<AbstractMixerService> services;

    private static final URI DEFAULT_BASE_PATH = URI.create("https://mixer.com/api/v1/");

    public MixerAPI(String clientId) {
        this(clientId, null);
    }

    public MixerAPI(String clientId, String oauthToken) {
        this(clientId, DEFAULT_BASE_PATH, oauthToken);
    }

    public MixerAPI(String clientId, URI basePath, String oauthToken) {
        if (basePath != null) {
            this.basePath = basePath;
        } else {
            this.basePath = DEFAULT_BASE_PATH;
        }

        this.gson = new GsonBuilder()
                .registerTypeAdapter(InetSocketAddress.class, new InetSocketAddressAdapter())
                .registerTypeAdapter(URI.class, new URIAdapter())
                .registerTypeAdapter(Date.class, DateAdapter.v1())
                .create();

        this.executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        this.http = new MixerHttpClient(this, clientId, oauthToken);
        this.services = new ServiceManager<>();

        this.register(new UsersService(this));
        this.register(new ChatService(this));
        this.register(new EmotesService(this));
        this.register(new ChannelsService(this));
        this.register(new TypesService(this));
        this.register(new InteractiveService(this));
        this.register(new JWTService(this));
    }

    public <T extends AbstractMixerService> T use(Class<T> service) {
        return this.services.get(service);
    }

    public boolean register(AbstractMixerService service) {
        return this.services.register(service);
    }
    public void setUserAgent(String agent) {
        this.http.setUserAgent(agent);
    }
}
