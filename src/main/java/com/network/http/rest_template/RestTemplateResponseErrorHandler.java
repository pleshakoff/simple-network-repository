package com.network.http.rest_template;

import com.network.http.HttpException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;


public class RestTemplateResponseErrorHandler
        implements ResponseErrorHandler {



    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {
        return (httpResponse.getStatusCode().series() == CLIENT_ERROR
                                || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        HttpStatus statusCode = HttpStatus.resolve(httpResponse.getRawStatusCode());
          throw new RuntimeException(String.format("Remote procedure call error. Status code %s",statusCode));
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse httpResponse) throws IOException {
      HttpStatus statusCode = HttpStatus.resolve(httpResponse.getRawStatusCode());
      throw new HttpException("Remote procedure call error",statusCode,method,url);

    }
}