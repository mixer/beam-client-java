package com.mixer.api.http.ws;

import com.google.common.base.Joiner;
import com.mixer.api.http.MixerHttpClient;
import org.apache.http.cookie.Cookie;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshakeBuilder;

import java.util.ArrayList;
import java.util.List;

public class CookieDraft_6455 extends Draft_6455 {
    protected final MixerHttpClient httpClient;

    public CookieDraft_6455(MixerHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public ClientHandshakeBuilder postProcessHandshakeRequestAsClient(ClientHandshakeBuilder request) {
        List<String> cookies = new ArrayList<>();
        for (Cookie cookie : this.httpClient.cookieStore.getCookies()) {
            cookies.add(cookie.getName() + "=" + cookie.getValue());
        }

        request.put("Cookie", Joiner.on("; ").join(cookies));

        return super.postProcessHandshakeRequestAsClient(request);
    }

    @Override
    public Draft copyInstance() {
        return new CookieDraft_6455(this.httpClient);
    }
}
