package com.network.http;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public interface Http {

    default  <T> ResponseEntity<T> callGet(String serviceName,
                                            Class<T> responseType,
                                            Map<String, String> additionalHeaders,
                                            MultiValueMap<String, String> queryParams,
                                            String... pathSegments
    ){
        return call(serviceName,HttpMethod.GET,responseType,null, additionalHeaders,queryParams,pathSegments);
    };

    default  <T> ResponseEntity<T> callGet(String serviceName,
                                           Class<T> responseType,
                                           MultiValueMap<String, String> queryParams,
                                           String... pathSegments
    ){
        return call(serviceName,HttpMethod.GET,responseType,null, null,queryParams,pathSegments);
    };

    default  <T> ResponseEntity<T> callGet(String serviceName,
                                           Class<T> responseType,
                                           String... pathSegments
    ){
        return call(serviceName,HttpMethod.GET,responseType,null, null,null,pathSegments);
    };



    default  <T,B> ResponseEntity<T> callPost(String serviceName,
                                           Class<T> responseType,
                                           @Nullable B body,
                                           Map<String, String> additionalHeaders,
                                           String... pathSegments
    ){
        return call(serviceName,HttpMethod.POST,responseType,body, additionalHeaders,null,pathSegments);
    };

    default  <T,B> ResponseEntity<T> callPost(String serviceName,
                                              Class<T> responseType,
                                              @Nullable B body,
                                              String... pathSegments
    ){
        return call(serviceName,HttpMethod.POST,responseType,body, null,null,pathSegments);
    };

    default  <T,B> ResponseEntity<T> callPut(String serviceName,
                                              Class<T> responseType,
                                              @Nullable B body,
                                              Map<String, String> additionalHeaders,
                                              String... pathSegments
    ){
        return call(serviceName,HttpMethod.PUT,responseType,body, additionalHeaders,null,pathSegments);
    };

    default  <T,B> ResponseEntity<T> callPut(String serviceName,
                                              Class<T> responseType,
                                              @Nullable B body,
                                              String... pathSegments
    ){
        return call(serviceName,HttpMethod.PUT,responseType,body, null,null,pathSegments);
    };

    default  ResponseEntity<String> callDelete(String serviceName,
                                               Map<String, String> additionalHeaders,
                                               String... pathSegments
    ){
        return call(serviceName,HttpMethod.DELETE,null,null, additionalHeaders,null,pathSegments);
    };

    default  ResponseEntity<String> callDelete(String serviceName,
                                               String... pathSegments
    ){
        return call(serviceName,HttpMethod.DELETE,null,null, null,null,pathSegments);
    };


    <T,B> ResponseEntity<T> call(String serviceName,
                                 HttpMethod method,
                                 Class<T> responseType,
                                 @Nullable B body,
                                 @Nullable Map<String, String> additionalHeaders,
                                 @Nullable  MultiValueMap<String,String> queryParams,
                                 @Nullable String... pathSegments
    );
}
