package com.mixer.api.http;

import org.apache.http.StatusLine;

public class HttpCompleteResponse {
    protected final StatusLine status;
    protected final String body;

    public HttpCompleteResponse(StatusLine status, String body) {
        this.status = status;
        this.body = body;
    }

    public int status() {
        return this.status.getStatusCode();
    }

    public String body() {
        return this.body;
    }
}
