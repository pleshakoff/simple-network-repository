package com.network.http;

import com.network.ServicesProps;
import com.network.http.rest_template.RestTemplateAdapter;
import com.network.http.rest_template.RestTemplateResponseErrorHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(ServicesProps.class)
public class HttpConfiguration {

    @Bean
    RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.errorHandler(restTemplateResponseErrorHandler()).build();
    }

    @Bean
    public Http network(ServicesProps servicesProps, RestTemplateBuilder builder){
        return new RestTemplateAdapter(servicesProps,restTemplate(builder));
    }

}
