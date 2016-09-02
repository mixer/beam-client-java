package pro.beam.api.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpCompleteResponseHandler implements ResponseHandler<HttpCompleteResponse> {
    @Override
    public HttpCompleteResponse handleResponse(HttpResponse response) throws IOException {
        StatusLine statusLine = response.getStatusLine();

        if (statusLine.getStatusCode() == 204) {
            return new HttpCompleteResponse(statusLine, null);
        }
        HttpEntity entity = response.getEntity();

        return new HttpCompleteResponse(statusLine, EntityUtils.toString(entity));
    }
}
