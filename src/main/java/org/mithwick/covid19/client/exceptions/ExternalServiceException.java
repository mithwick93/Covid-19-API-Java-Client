package org.mithwick.covid19.client.exceptions;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ExternalServiceException extends RuntimeException {
    private final String method;
    private final String url;
    private final String requestBody;
    private final int statusCode;
    private final String responseBody;

    public ExternalServiceException(String method, String url, String requestBody, int statusCode, String responseBody) {
        super(responseBody);
        this.method = method;
        this.url = url;
        this.requestBody = requestBody;
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public ExternalServiceException(String method, String url, int statusCode, String responseBody) {
        this(method, url, null, statusCode, responseBody);
    }
}
