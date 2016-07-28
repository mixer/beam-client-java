package pro.beam.api.test.unit.http;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.mockito.Spy;
import pro.beam.api.BeamAPI;
import pro.beam.api.http.BeamHttpClient;
import pro.beam.api.http.RequestType;
import pro.beam.api.resource.BeamUser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class BeamHttpClientTest {
    private HttpClient defaultHttpClient;

    private BeamHttpClient beamHttpClient;

    private BeamAPI beamAPI;

    @Before
    public void setup() {

    }

    @Test
    public void itHandlesCSRF() throws IOException, ExecutionException, InterruptedException {
        beamAPI = new BeamAPI();
        defaultHttpClient = mock(HttpClient.class);

        // This is done because Mockito is not able to mock methods that are called within the class'
        // Constructor.
        class MockableBeamHttpClient extends BeamHttpClient {
            public MockableBeamHttpClient(BeamAPI beam) {
                super(beam);
            }

            @Override
            protected HttpClient buildHttpClient() {
                return defaultHttpClient;
            }
        }
        beamHttpClient = new MockableBeamHttpClient(beamAPI);


        HttpResponse csrfResponse = prepareResponse(461, "{\"error\":\"Invalid or missing CSRF header, see details here: <https://dev.beam.pro/rest.html#csrf>\",\"statusCode\":461}");
        csrfResponse.addHeader(new BasicHeader(beamHttpClient.CSRF_TOKEN_LOCATION, "abc123"));
        csrfResponse.setEntity(mock(HttpEntity.class));

        HttpResponse okResponse = prepareResponse(200, "{\"id\":314,\"userId\":344,\"token\":\"Beam\",\"online\":false,\"featured\":false,\"partnered\":false,\"transcodingProfileId\":1,\"suspended\":false,\"name\":\"Weekly Updates and more!\"}");
        csrfResponse.addHeader(new BasicHeader(beamHttpClient.CSRF_TOKEN_LOCATION, "abc123"));

        when(defaultHttpClient.execute(any(HttpUriRequest.class), isNull(HttpContext.class)))
                .thenReturn(csrfResponse)
                .thenReturn(okResponse);

        Futures.addCallback(beamHttpClient.get("/channels/314", BeamUser.class, null), new FutureCallback<BeamUser>() {
            @Override
            public void onSuccess(BeamUser beamUser) {
                Assert.assertEquals(beamHttpClient.getCsrfToken(), "abc123");
            }

            @Override
            public void onFailure(Throwable throwable) {
                Assert.fail();
            }
        });
    }

    private HttpResponse prepareResponse(int expectedResponseStatus, String expectedResponseBody) {
        HttpResponse response = new BasicHttpResponse(new BasicStatusLine(
                new ProtocolVersion("HTTP", 1, 1), expectedResponseStatus, ""));
        response.setStatusCode(expectedResponseStatus);
        try {
            response.setEntity(new StringEntity(expectedResponseBody));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
        return response;
    }
}
