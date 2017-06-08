package com.mixer.api.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.mixer.api.MixerAPI;
import com.mixer.api.http.MixerHttpClient;

import java.util.Map;

public abstract class AbstractHTTPService extends AbstractMixerService {
    protected final MixerHttpClient http;
    protected final String path;

    public AbstractHTTPService(MixerAPI mixer, String path) {
        super(mixer);
        this.http = this.mixer.http;
        this.path = path;
    }

    protected <T> ListenableFuture<T> get(String path, Class<T> type) {
        return this.get(path, type, null);
    }

    protected <T> ListenableFuture<T> get(String path, Class<T> type, Map<String, Object> parameters) {
        return this.http.get(this.path(path), type, parameters);
    }

    protected <T> ListenableFuture<T> patch(String path, Class<T> type, Object... args) {
        return this.http.patch(this.path(path), type, args);
    }

    protected <T> ListenableFuture<T> post(String path, Class<T> type, Object... args) {
        return this.http.post(this.path(path), type, args);
    }

    protected <T> ListenableFuture<T> put(String path, Class<T> type, Object... args) {
        return this.http.put(this.path(path), type, args);
    }

    protected <T> ListenableFuture<T> delete(String path, Class<T> type, Object... args) {
        return this.http.delete(this.path(path), type, args);
    }

    public String path(String relative) {
        if (this.path != null && !this.path.isEmpty()) {
            if (relative.length() > 0) {
                relative = "/" + relative;
            }
        }

        return this.mixer.basePath.resolve(this.path + relative).toString();
    }
}
