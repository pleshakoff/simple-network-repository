package com.network.http;

import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.net.URI;

@Getter
public class HttpException extends RuntimeException {

    private final URI url;
    private final HttpMethod method;
    private final HttpStatus statusCode;

    public HttpException(String message, HttpStatus statusCode, HttpMethod method, URI url) {
        super(message);
        this.statusCode = statusCode;
        this.method = method;
        this.url = url;
    }

    public HttpException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
        method = null;
        url = null;
    }

    @Override
    public String getMessage() {
        return (String.format("%s. Status code %s For %s %s",super.getMessage(),statusCode,method,url));
    }
}
