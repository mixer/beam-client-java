package com.mixer.api.http;

public class HttpBadResponseException extends Exception {
    public final HttpCompleteResponse response;

    public HttpBadResponseException(HttpCompleteResponse response) {
        this.response = response;
    }
}
