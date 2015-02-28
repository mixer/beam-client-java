package pro.beam.api.resource;

import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.client.WebSocketClient;
import pro.beam.api.http.ws.ConnectionProducer;
import pro.beam.api.resource.AbstractBeamResource;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public abstract class AbstractBeamConnectableResource<T extends WebSocketClient>
        extends AbstractBeamResource implements ConnectionProducer<T> {

    protected T useWSSProtocol(T connectable) {
        if (connectable == null) {
            return null;
        }

        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null,
                    this.trustAllCertificates(),
                    this.random());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            return null;
        }

        connectable.setWebSocketFactory(new DefaultSSLWebSocketClientFactory(sslContext));
        return connectable;
    }

    private TrustManager[] trustAllCertificates() {
        return new TrustManager[] {
            new X509TrustManager() {
                @Override public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException { }
                @Override public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException { }

                @Override public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }
        };
    }

    private SecureRandom random() {
        return new SecureRandom();
    }
}
