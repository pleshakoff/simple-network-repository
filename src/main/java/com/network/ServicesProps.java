package com.network;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "network")
public class ServicesProps {

    private List<Service> services =  new ArrayList<>();

    public Service getByName(String name){
       return services.stream().
               filter(service -> service.getName().equals(name)).
               findFirst().
               orElseThrow(() -> new RuntimeException("Unsupported service name "+name));

    }
}
