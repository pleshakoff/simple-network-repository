package com.network.http.rest_template;

import com.network.Service;
import com.network.ServicesProps;
import com.network.http.Http;
import com.network.http.HttpException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class RestTemplateAdapter implements Http {

    private final ServicesProps servicesProps;
    private  final RestTemplate restTemplate;



    private UriComponentsBuilder getUriBuilder(String serviceName) {
        Service service = servicesProps.getByName(serviceName);
        return  UriComponentsBuilder.newInstance()
                .scheme(RestTemplateUtils.SCHEME).
                        host(service.getHost()).
                        port(service.getPort()).
                        pathSegment(RestTemplateUtils.API,service.getVersion());
    }



     @Override
     public  <T,B> ResponseEntity<T> call(String serviceName,
                                          HttpMethod method,
                                          Class<T> responseType,
                                          @Nullable B body,
                                          Map<String, String> additionalHeaders,
                                          MultiValueMap<String, String> queryParams,
                                          String... pathSegments
     )
     {
       return call(serviceName,method,responseType,null,false,body,additionalHeaders,queryParams,pathSegments);
      }

    @Override
    public  <T,B> ResponseEntity<T> call(String serviceName,
                                         HttpMethod method,
                                         ParameterizedTypeReference<T> responseTypeList,
                                         @Nullable B body,
                                         Map<String, String> additionalHeaders,
                                         MultiValueMap<String, String> queryParams,
                                         String... pathSegments
    )
    {
        return call(serviceName,method,null,responseTypeList,true,body,additionalHeaders,queryParams,pathSegments);

    }


    private  <T,B> ResponseEntity<T> call(String serviceName,
                                         HttpMethod method,
                                         Class<T> responseType,
                                         ParameterizedTypeReference<T> responseTypeList,
                                         boolean isList,
                                         @Nullable B body,
                                         Map<String, String> additionalHeaders,
                                         MultiValueMap<String, String> queryParams,
                                         String... pathSegments
    )
    {
        URI uri = getUriBuilder(serviceName).queryParams(queryParams).pathSegment(pathSegments).build().toUri();


        HttpHeaders httpHeaders = RestTemplateUtils.getHttpHeaders();

        if (additionalHeaders != null)
            additionalHeaders.forEach(httpHeaders::set);


        HttpEntity<B> httpEntity = new HttpEntity<>(body, httpHeaders);
        log.info("Call {}",uri.toString());

        ResponseEntity<T> responseEntity;
        if (isList) {
            responseEntity = restTemplate.exchange(uri, method, httpEntity, responseTypeList);
        }
        else
         responseEntity = restTemplate.exchange(uri, method, httpEntity, responseType);

        if (responseEntity.getStatusCode()== HttpStatus.OK) {
            return responseEntity;
        }
        else
        {
            throw new HttpException("Remote call error",responseEntity.getStatusCode());
        }
    }






}
