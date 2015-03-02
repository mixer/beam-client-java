package pro.beam.api.http.ws;

import com.google.common.base.Joiner;
import org.apache.http.cookie.Cookie;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ClientHandshakeBuilder;
import pro.beam.api.http.BeamHttpClient;

import java.util.ArrayList;
import java.util.List;

public class CookieDraft_17 extends Draft_17 {
    protected final BeamHttpClient httpClient;

    public CookieDraft_17(BeamHttpClient httpClient) {
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
        return new CookieDraft_17(this.httpClient);
    }
}
