package pro.beam.api.test.unit.http;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.gson.Gson;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.protocol.HttpContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pro.beam.api.BeamAPI;
import pro.beam.api.http.BeamHttpClient;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.resource.user.JSONWebToken;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static pro.beam.api.http.BeamHttpClient.CSRF_TOKEN_HEADER;
import static pro.beam.api.http.BeamHttpClient.X_JWT_HEADER;

public class BeamHttpClientTest {
    private HttpClient defaultHttpClient;

    private BeamHttpClient beamHttpClient;

    private BeamAPI beamAPI;

    @Before
    public void setup() {

    }

    @Test
    public void itHandlesCSRF() {
        beamAPI = new BeamAPI();
        defaultHttpClient = mock(HttpClient.class);

        // This is done because Mockito is not able to mock methods that are called within the class'
        // Constructor.
        class MockableBeamHttpClient extends BeamHttpClient {
            private MockableBeamHttpClient(BeamAPI beam) {
                super(beam);
            }

            @Override
            protected HttpClient buildHttpClient() {
                return defaultHttpClient;
            }
        }
        beamHttpClient = new MockableBeamHttpClient(beamAPI);


        HttpResponse csrfResponse = prepareResponse(461, "{\"error\":\"Invalid or missing CSRF header, see details here: <https://dev.beam.pro/rest.html#csrf>\",\"statusCode\":461}");
        csrfResponse.addHeader(new BasicHeader(CSRF_TOKEN_HEADER, "abc123"));
        csrfResponse.setEntity(mock(HttpEntity.class));

        HttpResponse okResponse = prepareResponse(200, "{\"id\":314,\"userId\":344,\"token\":\"Beam\",\"online\":false,\"featured\":false,\"partnered\":false,\"transcodingProfileId\":1,\"suspended\":false,\"name\":\"Weekly Updates and more!\"}");
        okResponse.addHeader(new BasicHeader(CSRF_TOKEN_HEADER, "abc123"));

        try {
            doReturn(csrfResponse)
                    .doReturn(okResponse)
                    .when(defaultHttpClient)
                    .execute(any(HttpUriRequest.class), isNull(HttpContext.class));
        } catch (IOException e) {
            Assert.fail();
        }

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

    @Test
    public void itHandlesJWT() {
        beamAPI = new BeamAPI();
        defaultHttpClient = mock(HttpClient.class);

        final String jwtString = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjU3NDk5NSwiYnR5cCI6ImdyYW50IiwiZ3JvdXBzIjpbeyJpZCI6MSwibmFtZSI6IlVzZXIiLCJVc2VyR3JvdXAiOnsiZ3JvdXBJZCI6MSwidXNlcklkIjo1NzQ5OTV9fV0sImlhdCI6MTQ4MTE0NDQ0NywiZXhwIjoxNTEyNjgwNDQ3LCJpc3MiOiJCZWFtIiwianRpIjoiVGhpcyBpc24ndCBhIHJlYWwgc2Vzc2lvbi4ifQ==.HkL5xq-eivwCk5OgczgIu5s_NFFxdQAKH9Jfb906aT4";

        final JSONWebToken.Group jwtGroup = new JSONWebToken.Group();
        jwtGroup.id = 1L;
        jwtGroup.name = "User";

        final JSONWebToken jwt = new JSONWebToken();
        jwt.exp = 1512680447L;
        jwt.iat = 1481144447L;
        jwt.iss = "Beam";
        jwt.jti = "This isn't a real session.";
        jwt.sub = 574995L;
        jwt.btyp = "grant";
        jwt.groups = new JSONWebToken.Group[]{jwtGroup};

        // This is done because Mockito is not able to mock methods that are called within the class'
        // Constructor.
        class MockableBeamHttpClient extends BeamHttpClient {
            private MockableBeamHttpClient(BeamAPI beam) {
                super(beam);
            }

            @Override
            protected HttpClient buildHttpClient() {
                return defaultHttpClient;
            }
        }

        beamHttpClient = new MockableBeamHttpClient(beamAPI);

        HttpResponse okResponse = prepareResponse(200, "{\"id\":314,\"userId\":344,\"token\":\"Beam\",\"online\":false,\"featured\":false,\"partnered\":false,\"transcodingProfileId\":1,\"suspended\":false,\"name\":\"Weekly Updates and more!\"}");
        okResponse.addHeader(new BasicHeader(X_JWT_HEADER, jwtString));

        try {
            doReturn(okResponse).when(defaultHttpClient).execute(any(HttpUriRequest.class), isNull(HttpContext.class));
        } catch (IOException e) {
            Assert.fail();
        }

        Futures.addCallback(beamHttpClient.get("/channels/314", BeamUser.class, null), new FutureCallback<BeamUser>() {
            @Override
            public void onSuccess(BeamUser beamUser) {
                Assert.assertEquals(beamHttpClient.getJWTString(), jwtString);
                Assert.assertEquals(beamHttpClient.getJwt(), jwt);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Assert.fail();
            }
        });
    }
}
